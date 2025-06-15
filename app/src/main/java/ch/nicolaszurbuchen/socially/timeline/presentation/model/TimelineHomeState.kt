package ch.nicolaszurbuchen.socially.timeline.presentation.model

import com.google.firebase.firestore.DocumentSnapshot

data class TimelineHomeState(
    val posts: List<TimelineHomePostState> = emptyList(),
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = true,
    val hasMore: Boolean = true,
    val lastSnapshot: DocumentSnapshot? = null,
    val hasError: Boolean = false,
)
