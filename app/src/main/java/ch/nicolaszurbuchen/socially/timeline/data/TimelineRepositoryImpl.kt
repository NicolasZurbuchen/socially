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
}