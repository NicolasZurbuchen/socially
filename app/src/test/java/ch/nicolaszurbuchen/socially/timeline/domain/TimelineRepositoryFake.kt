package ch.nicolaszurbuchen.socially.timeline.domain

import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomeEntity
import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomePostEntity
import com.google.firebase.firestore.DocumentSnapshot
import io.mockk.every
import io.mockk.mockk
import java.io.InputStream

class TimelineRepositoryFake : TimelineRepository {

    private val postList = generateMockPosts().sortedByDescending { it.timestamp }

    override suspend fun createNewPost(title: String?, imageStream: InputStream?): Result<Unit> {
        return if (title != null || imageStream != null) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Invalid post"))
        }
    }

    override suspend fun getPosts(
        pageSize: Int,
        lastVisible: DocumentSnapshot?
    ): Result<TimelineHomeEntity> {
        val startIndex = lastVisible?.id?.toIntOrNull()?.minus(1)?.plus(1) ?: 0

        if (startIndex >= postList.size) {
            return Result.success(TimelineHomeEntity(emptyList(), null, hasMore = false))
        }

        val page = postList.drop(startIndex).take(pageSize)
        val newLastVisible = page.lastOrNull()?.let {
            mockk<DocumentSnapshot>(relaxed = true).apply {
                every { id } returns it.id
            }
        }
        val hasMore = startIndex + pageSize < postList.size

        return Result.success(TimelineHomeEntity(page, newLastVisible, hasMore))
    }

    companion object {
        private fun generateMockPosts(count: Int = 12): List<TimelineHomePostEntity> {
            return List(count) { i ->
                TimelineHomePostEntity(
                    id = (i + 1).toString(),
                    post = "Post ${i + 1}",
                    username = "user${i + 1}",
                    imageUrl = "https://example.com/image${i + 1}.jpg",
                    timestamp = System.currentTimeMillis() - i * 1000L
                )
            }
        }
    }
}