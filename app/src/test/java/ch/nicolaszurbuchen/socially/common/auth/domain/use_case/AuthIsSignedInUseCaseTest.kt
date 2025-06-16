package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthIsSignedInUseCaseTest : AuthBaseUseCase() {

    private lateinit var useCase: AuthIsSignedInUseCase

    @Before
    fun setup() {
        useCase = AuthIsSignedInUseCase(fakeRepository)
    }

    @Test
    fun `isUserSignedIn returns true if signed in`() = runTest {
        fakeRepository.signedIn = true
        val result = useCase()
        assertTrue(result)
    }

    @Test
    fun `isUserSignedIn returns false if not signed in`() = runTest {
        fakeRepository.signedIn = false
        val result = useCase()
        assertFalse(result)
    }
}