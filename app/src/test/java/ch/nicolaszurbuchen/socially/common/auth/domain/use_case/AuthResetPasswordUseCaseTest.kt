package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import ch.nicolaszurbuchen.socially.common.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthResetPasswordUseCaseTest : AuthBaseUseCase() {

    private lateinit var useCase: AuthResetPasswordUseCase

    @Before
    fun setup() {
        useCase = AuthResetPasswordUseCase(fakeRepository)
    }

    @Test
    fun `resetPassword returns success`() = runTest {
        val result = useCase("email@example.com")
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `resetPassword returns error when repository fails`() = runTest {
        fakeRepository.shouldFail = true
        val result = useCase("email@example.com")
        assertTrue(result is Resource.Failure)
    }
}