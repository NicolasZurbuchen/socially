package ch.nicolaszurbuchen.socially.timeline.domain.model

data class TimelineHomePostEntity(
    val id: String,
    val username: String,
    val post: String?,
    val imageUrl: String?,
    val timestamp: Long,
)
