package ch.nicolaszurbuchen.socially.timeline.data

import android.net.Uri
import ch.nicolaszurbuchen.socially.timeline.domain.TimelineRepository
import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomeEntity
import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomePostEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TimelineRepositoryImpl(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
): TimelineRepository {

    override suspend fun createNewPost(title: String?, imageUri: Uri?): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))

            val imageUrl = imageUri?.let {
                val fileName = "posts/${UUID.randomUUID()}.jpg"
                val ref = storage.reference.child(fileName)
                ref.putFile(it).await()
                ref.downloadUrl.await().toString()
            }

            val post = mapOf(
                "userId" to userId,
                "title" to title,
                "imageUrl" to imageUrl,
                "timestamp" to System.currentTimeMillis()
            )

            firestore.collection("posts").add(post).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPosts(
        pageSize: Int,
        lastVisible: DocumentSnapshot?,
    ): Result<TimelineHomeEntity> {
        return try {
            var query = firestore.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(pageSize.toLong())

            if (lastVisible != null) {
                query = query.startAfter(lastVisible)
            }

            val postsSnapshot = query.get().await()

            val postDocs = postsSnapshot.documents
            val last = postDocs.lastOrNull()

            if (postDocs.isEmpty()) {
                return Result.success(TimelineHomeEntity(emptyList(), last, false))
            }

            val userIds = postDocs.mapNotNull { it.getString("userId") }.distinct()
            val usersSnapshot = firestore.collection("users")
                .whereIn(FieldPath.documentId(), userIds)
                .get().await()

            val usernamesById = usersSnapshot.documents.associate {
                it.id to (it.getString("username") ?: "Unknown")
            }

            val posts = postDocs.mapNotNull { doc ->
                val userId = doc.getString("userId") ?: return@mapNotNull null
                val username = usernamesById[userId] ?: "Unknown"

                TimelineHomePostEntity(
                    id = doc.id,
                    username = username,
                    post = doc.getString("title"),
                    imageUrl = doc.getString("imageUrl"),
                    timestamp = doc.getLong("timestamp") ?: 0L,
                )
            }

            val hasMore = posts.size == pageSize
            Result.success(TimelineHomeEntity(posts, last, hasMore))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}