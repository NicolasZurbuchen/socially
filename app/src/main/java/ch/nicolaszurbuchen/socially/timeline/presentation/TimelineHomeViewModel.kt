package ch.nicolaszurbuchen.socially.timeline.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthSignOutUseCase
import ch.nicolaszurbuchen.socially.timeline.domain.TimelineGetPostsUseCase
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomePostState
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineHomeViewModel @Inject constructor(
    private val timelineGetPostsUseCase: TimelineGetPostsUseCase,
    private val authSignOutUseCase: AuthSignOutUseCase,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<Unit>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = MutableStateFlow(TimelineHomeState())
    val state: StateFlow<TimelineHomeState>
        get() = _state

    private var pagingJob: Job? = null

    init {
        refresh()
    }

    fun signOut() {
        viewModelScope.launch {
            authSignOutUseCase()
            _eventFlow.emit(Unit)
        }
    }

    fun refresh() {
        pagingJob?.cancel()

        _state.update { TimelineHomeState() }
        val state = _state.value

        pagingJob = viewModelScope.launch {
            val result = timelineGetPostsUseCase(state.lastSnapshot)

            _state.update {
                if (result.isSuccess) {
                    val page = result.getOrThrow()
                    it.copy(
                        posts = page.posts.map { TimelineHomePostState.fromEntity(it) },
                        lastSnapshot = page.lastSnapshot,
                        isLoadingMore = false,
                        isRefreshing = false,
                        hasMore = page.hasMore,
                    )
                } else {
                    it.copy(
                        isLoadingMore = false,
                        isRefreshing = false,
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        val state = _state.value
        if (state.isLoadingMore || state.isRefreshing || !state.hasMore) return

        pagingJob?.cancel()
        _state.update { it.copy(isLoadingMore = true, hasError = false) }

        pagingJob = viewModelScope.launch {
            val result = timelineGetPostsUseCase(state.lastSnapshot)

            _state.update {
                if (result.isSuccess) {
                    val page = result.getOrNull()!!
                    it.copy(
                        posts = it.posts + page.posts.map { TimelineHomePostState.fromEntity(it) },
                        lastSnapshot = page.lastSnapshot,
                        isLoadingMore = false,
                        isRefreshing = false,
                        hasMore = page.hasMore,
                    )
                } else {
                    it.copy(
                        isLoadingMore = false,
                        isRefreshing = false,
                        hasError = true,
                    )
                }
            }
        }
    }
}