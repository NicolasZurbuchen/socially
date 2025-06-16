package ch.nicolaszurbuchen.socially.timeline.domain.use_case

import ch.nicolaszurbuchen.socially.timeline.domain.TimelineRepository
import java.io.InputStream
import javax.inject.Inject

class TimelineCreateNewPostUseCase @Inject constructor(
    private val repository: TimelineRepository,
) {
    suspend operator fun invoke(title: String?, imageStream: InputStream?): Result<Unit> {
        return repository.createNewPost(title, imageStream)
    }
}