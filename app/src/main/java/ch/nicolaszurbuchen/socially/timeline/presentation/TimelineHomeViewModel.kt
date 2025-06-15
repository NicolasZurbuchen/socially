package ch.nicolaszurbuchen.socially.timeline.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.timeline.domain.TimelineGetPostsUseCase
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomePostState
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineHomeViewModel @Inject constructor(
    private val timelineGetPostsUseCase: TimelineGetPostsUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(TimelineHomeState())
    val state: StateFlow<TimelineHomeState>
        get() = _state

    init {
        loadNextPage()
    }

    fun logout() {

    }

    fun refresh() {

    }

    fun loadNextPage() {
        val state = _state.value

        if (state.isLoadingMore || state.isRefreshing || !state.hasMore) return

        _state.update { it.copy(isLoadingMore = true) }

        viewModelScope.launch {
            val result = timelineGetPostsUseCase(state.lastSnapshot)

            _state.update {
                if (result.isSuccess) {
                    val page = result.getOrNull()!!
                    it.copy(
                        posts = it.posts + page.posts.map { TimelineHomePostState.fromEntity(it) },
                        lastSnapshot = page.lastSnapshot,
                        isLoadingMore = false,
                        hasMore = page.hasMore,
                    )
                } else {
                    it.copy(
                        isLoadingMore = false,
                    )
                }
            }
        }
    }
}