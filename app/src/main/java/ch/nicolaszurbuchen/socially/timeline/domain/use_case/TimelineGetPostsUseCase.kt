package ch.nicolaszurbuchen.socially.timeline.domain.use_case

import ch.nicolaszurbuchen.socially.timeline.domain.TimelineRepository
import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomeEntity
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class TimelineGetPostsUseCase @Inject constructor(
    private val repository: TimelineRepository,
    private val pageSize: Int = 10,
) {
    suspend operator fun invoke(lastVisible: DocumentSnapshot?): Result<TimelineHomeEntity> {
        val result = repository.getPosts(pageSize, lastVisible)

        return result.map { page ->
            TimelineHomeEntity(
                posts = page.posts,
                lastSnapshot = page.lastSnapshot,
                hasMore = page.posts.size == pageSize,
            )
        }
    }
}