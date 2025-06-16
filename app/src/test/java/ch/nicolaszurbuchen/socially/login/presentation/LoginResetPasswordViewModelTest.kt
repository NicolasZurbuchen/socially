package ch.nicolaszurbuchen.socially.login.presentation

import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.auth.domain.use_case.AuthResetPasswordUseCase
import ch.nicolaszurbuchen.socially.common.presentation.BaseViewModelTest
import ch.nicolaszurbuchen.socially.common.utils.DomainError
import ch.nicolaszurbuchen.socially.common.utils.FirebaseErrorType
import ch.nicolaszurbuchen.socially.common.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginResetPasswordViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: LoginResetPasswordViewModel
    private val resetPasswordUseCase = mockk<AuthResetPasswordUseCase>()

    @Before
    fun setup() {
        viewModel = LoginResetPasswordViewModel(resetPasswordUseCase)
    }

    @Test
    fun `updateEmail updates state correctly`() = runTest {
        viewModel.updateEmail("test@example.com")
        assertEquals("test@example.com", viewModel.state.value.email)
    }

    @Test
    fun `sendEmail sets loading true then success`() = runTest {
        coEvery { resetPasswordUseCase("test@example.com") } returns Resource.Success(Unit)

        viewModel.updateEmail("test@example.com")
        viewModel.sendEmail()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.success)
        assertNull(state.emailError)
        assertNull(state.error)
    }

    @Test
    fun `sendEmail shows email error on invalid email`() = runTest {
        coEvery { resetPasswordUseCase("invalid") } returns Resource.Failure(DomainError.InvalidEmail)

        viewModel.updateEmail("invalid")
        viewModel.sendEmail()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(R.string.error_invalid_email, state.emailError)
        assertFalse(state.isLoading)
        assertFalse(state.success)
    }

    @Test
    fun `sendEmail shows firebase error box`() = runTest {
        coEvery {
            resetPasswordUseCase("test@example.com")
        } returns Resource.Failure(DomainError.Firebase(FirebaseErrorType.Unknown))

        viewModel.updateEmail("test@example.com")
        viewModel.sendEmail()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(
            R.string.error_something_wrong,
            state.error?.title
        )
        assertEquals(
            R.string.error_something_wrong_description,
            state.error?.description
        )
        assertFalse(state.isLoading)
        assertFalse(state.success)
    }

    @Test
    fun `sendEmail sets loading during call`() = runTest {
        val result = Resource.Success(Unit)
        coEvery { resetPasswordUseCase(any()) } coAnswers {
            assertTrue(viewModel.state.value.isLoading)
            result
        }

        viewModel.updateEmail("test@example.com")
        viewModel.sendEmail()

        assertFalse(viewModel.state.value.isLoading)
    }
}
