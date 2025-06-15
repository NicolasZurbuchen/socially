package ch.nicolaszurbuchen.socially.timeline.presentation.model

import android.net.Uri

data class TimelineNewPostState(
    val post: String = "",
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
) {

    val isImageUploaded: Boolean
        get() = imageUri != null

    val interactionEnabled: Boolean
        get() = !isLoading

    val isPostButtonEnabled: Boolean
        get() = (post.isNotEmpty() || imageUri != null) && interactionEnabled
}
