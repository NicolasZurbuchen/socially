package ch.nicolaszurbuchen.socially.timeline.domain.use_case

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TimelineGetPostsUseCaseTest : TimelineBaseUseCase() {

    private lateinit var useCase: TimelineGetPostsUseCase

    @Before
    fun setup() {
        useCase = TimelineGetPostsUseCase(fakeRepository, pageSize = 10)
    }

    @Test
    fun `getPosts returns first page with hasMore true`() = runTest {
        val result = useCase(null)

        assertTrue(result.isSuccess)
        val page = result.getOrNull()
        assertNotNull(page)
        assertEquals(10, page!!.posts.size)
        assertTrue(page.hasMore)
    }

    @Test
    fun `getPosts returns second page with hasMore false`() = runTest {
        val firstPage = useCase(null).getOrThrow()
        val lastSnapshot = firstPage.lastSnapshot

        val result = useCase(lastSnapshot)

        assertTrue(result.isSuccess)
        val page = result.getOrNull()
        assertNotNull(page)
        assertEquals(2, page!!.posts.size)
        assertFalse(page.hasMore)
    }

    @Test
    fun `getPosts returns empty list when all pages exhausted`() = runTest {
        val first = useCase(null).getOrThrow()
        val second = useCase(first.lastSnapshot).getOrThrow()
        val third = useCase(second.lastSnapshot).getOrThrow()

        assertTrue(third.posts.isEmpty())
        assertFalse(third.hasMore)
    }
}
