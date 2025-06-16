package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class AuthSignOutUseCaseTest : AuthBaseUseCase() {

    private lateinit var useCase: AuthSignOutUseCase

    @Before
    fun setup() {
        useCase = AuthSignOutUseCase(fakeRepository)
    }

    @Test
    fun `signOut updates sign-in state`() = runTest {
        fakeRepository.signedIn = true
        useCase()
        assertFalse(fakeRepository.signedIn)
    }
}