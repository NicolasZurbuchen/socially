package ch.nicolaszurbuchen.socially.timeline.domain

import android.net.Uri
import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomeEntity
import com.google.firebase.firestore.DocumentSnapshot

interface TimelineRepository {

    suspend fun createNewPost(title: String?, imageUri: Uri?): Result<Unit>

}