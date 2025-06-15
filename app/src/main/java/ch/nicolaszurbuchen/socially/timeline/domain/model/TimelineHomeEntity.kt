package ch.nicolaszurbuchen.socially.timeline.domain.model

import com.google.firebase.firestore.DocumentSnapshot

data class TimelineHomeEntity(
    val posts: List<TimelineHomePostEntity>,
    val lastSnapshot: DocumentSnapshot?,
    val hasMore: Boolean,
)