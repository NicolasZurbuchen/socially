package ch.nicolaszurbuchen.socially.timeline.domain

import android.net.Uri
import ch.nicolaszurbuchen.socially.timeline.domain.model.TimelineHomeEntity
import com.google.firebase.firestore.DocumentSnapshot
import java.io.InputStream

interface TimelineRepository {

    suspend fun createNewPost(title: String?, imageStream: InputStream?): Result<Unit>

    suspend fun getPosts(
        pageSize: Int,
        lastVisible: DocumentSnapshot?,
    ): Result<TimelineHomeEntity>
}