package ch.nicolaszurbuchen.socially.timeline.domain

import android.net.Uri // TODO not good
import javax.inject.Inject

class TimelineCreateNewPostUseCase @Inject constructor(
    private val repository: TimelineRepository,
) {
    suspend operator fun invoke(title: String?, imageUri: Uri?): Result<Unit> {
        return repository.createNewPost(title, imageUri)
    }
}