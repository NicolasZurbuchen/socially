package ch.nicolaszurbuchen.socially.timeline.domain

import android.net.Uri // TODO not good
import javax.inject.Inject

class TimelineCreateNewPostUseCase @Inject constructor(
    private val repository: TimelineRepository,
) {
    suspend operator fun invoke(title: String?, imageUri: Uri?): Result<Unit> {
        if ((title.isNullOrBlank() && imageUri == null)) {
            return Result.failure(IllegalArgumentException("Post must have at least text or image"))
        }
        return repository.createNewPost(title, imageUri)
    }
}