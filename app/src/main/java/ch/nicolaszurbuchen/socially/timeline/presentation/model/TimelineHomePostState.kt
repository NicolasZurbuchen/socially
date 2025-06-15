package ch.nicolaszurbuchen.socially.timeline.presentation.model

import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomePostEntity

data class TimelineHomePostState(
    val id: String = "",
    val username: String = "",
    val post: String? = null,
    val imageUrl: String? = null,
    val timestamp: Long = 0,
) {

    val usernameLetter: Char
        get() = username.first().uppercaseChar()

    val hasText: Boolean
        get() =!post.isNullOrEmpty()

    val hasImage: Boolean
        get() = imageUrl != null
}
