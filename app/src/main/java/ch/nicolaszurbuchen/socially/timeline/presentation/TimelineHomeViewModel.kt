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

    }
}