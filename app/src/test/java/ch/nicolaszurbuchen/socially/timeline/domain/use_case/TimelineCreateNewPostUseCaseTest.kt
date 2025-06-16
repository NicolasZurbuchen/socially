package ch.nicolaszurbuchen.socially.timeline.domain.use_case

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class TimelineCreateNewPostUseCaseTest : TimelineBaseUseCase() {

    private lateinit var useCase: TimelineCreateNewPostUseCase

    @Before
    fun setup() {
        useCase = TimelineCreateNewPostUseCase(fakeRepository)
    }

    @Test
    fun `createNewPost returns success with valid title`() = runTest {
        val title = "Test Post"
        val inputStream = ByteArrayInputStream("image-bytes".toByteArray())
        val result = useCase(title, inputStream)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `createNewPost returns success with only image`() = runTest {
        val title = null
        val inputStream = ByteArrayInputStream("image-bytes".toByteArray())
        val result = useCase(title, inputStream)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `createNewPost returns success with only title`() = runTest {
        val title = "Text only post"
        val result = useCase(title, null)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `createNewPost returns failure when both title and image are null`() = runTest {
        val result = useCase(null, null)
        assertTrue(result.isFailure)
    }
}
