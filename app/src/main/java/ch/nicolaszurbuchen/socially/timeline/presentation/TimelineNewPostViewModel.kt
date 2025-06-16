package ch.nicolaszurbuchen.socially.timeline.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.timeline.domain.use_case.TimelineCreateNewPostUseCase
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineNewPostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimelineNewPostViewModel @Inject constructor(
    private val createNewPostUseCase: TimelineCreateNewPostUseCase,
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

    fun post(context: Context) {
        val state = _state.value

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasError = false) }

            val imageStream = state.imageUri?.let {
                context.contentResolver.openInputStream(it)
            }
            val result = createNewPostUseCase(state.post, imageStream)

            _state.update {
                if (result.isSuccess) {
                    it.copy(isLoading = false, success = true)
                } else {
                    it.copy(isLoading = false, hasError = true)
                }
            }

        }
    }
}