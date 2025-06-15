package ch.nicolaszurbuchen.socially.timeline.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.timeline.domain.TimelineCreateNewPostUseCase
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineNewPostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineNewPostViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(TimelineNewPostState())
    val state: StateFlow<TimelineNewPostState>
        get() = _state

    fun updatePost(post: String) {
        _state.update { it.copy(post = post) }
    }

    fun uploadImage(imageUri: Uri) {
        _state.update { it.copy(imageUri = imageUri) }
    }

    fun deleteImage() {
        _state.update { it.copy(imageUri = null) }
    }

    fun post() {
        viewModelScope.launch {

        }
    }
}