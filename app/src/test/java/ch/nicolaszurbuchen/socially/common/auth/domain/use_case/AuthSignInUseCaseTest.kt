package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import ch.nicolaszurbuchen.socially.common.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthSignInUseCaseTest : AuthBaseUseCase() {

    private lateinit var useCase: AuthSignInUseCase

    @Before
    fun setup() {
        useCase = AuthSignInUseCase(fakeRepository)
    }

    @Test
    fun `signIn returns success with valid input`() = runTest {
        val result = useCase("email@example.com", "password")
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `signIn returns error when credentials are invalid`() = runTest {
        fakeRepository.shouldFail = true
        val result = useCase("email@example.com", "wrongpass")
        assertTrue(result is Resource.Failure)
    }
}