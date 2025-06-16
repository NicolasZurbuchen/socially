package ch.nicolaszurbuchen.socially.login.presentation

import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.auth.domain.use_case.AuthSignInUseCase
import ch.nicolaszurbuchen.socially.common.presentation.BaseViewModelTest
import ch.nicolaszurbuchen.socially.common.utils.DomainError
import ch.nicolaszurbuchen.socially.common.utils.Field
import ch.nicolaszurbuchen.socially.common.utils.FieldValidationError
import ch.nicolaszurbuchen.socially.common.utils.FirebaseErrorType
import ch.nicolaszurbuchen.socially.common.utils.Resource
import ch.nicolaszurbuchen.socially.common.utils.ValidationErrors
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginSignInViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: LoginSignInViewModel
    private val signInUseCase = mockk<AuthSignInUseCase>()

    @Before
    fun setup() {
        viewModel = LoginSignInViewModel(signInUseCase)
    }

    @Test
    fun `updateEmail updates state correctly`() = runTest {
        viewModel.updateEmail("test@example.com")
        assertEquals("test@example.com", viewModel.state.value.email)
    }

    @Test
    fun `updatePassword updates state correctly`() = runTest {
        viewModel.updatePassword("123456")
        assertEquals("123456", viewModel.state.value.password)
    }

    @Test
    fun `togglePasswordVisibility toggles the state`() = runTest {
        val initial = viewModel.state.value.passwordVisible
        viewModel.togglePasswordVisibility()
        assertEquals(!initial, viewModel.state.value.passwordVisible)
    }

    @Test
    fun `signIn emits event and clears loading on success`() = runTest {
        coEvery { signInUseCase("test@example.com", "123456") } returns Resource.Success(Unit)

        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("123456")

        viewModel.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertNull(state.emailError)
        assertNull(state.passwordError)
    }

    @Test
    fun `signIn shows validation error for email`() = runTest {
        coEvery {
            signInUseCase("invalid", any())
        } returns Resource.Failure(
            ValidationErrors(listOf(FieldValidationError(Field.EMAIL, DomainError.InvalidUsername)))
        )

        viewModel.updateEmail("invalid")
        viewModel.updatePassword("123456")
        viewModel.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(R.string.error_invalid_email, state.emailError)
        assertFalse(state.isLoading)
    }

    @Test
    fun `signIn shows validation error for password`() = runTest {
        coEvery {
            signInUseCase(any(), "123")
        } returns Resource.Failure(
            ValidationErrors(listOf(FieldValidationError(Field.PASSWORD, DomainError.InvalidPassword)))
        )

        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("123")
        viewModel.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(R.string.error_invalid_password, state.passwordError)
        assertFalse(state.isLoading)
    }

    @Test
    fun `signIn shows generic error state on Firebase error`() = runTest {
        coEvery {
            signInUseCase("test@example.com", "123456")
        } returns Resource.Failure(DomainError.Firebase(FirebaseErrorType.Unknown))

        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("123456")
        viewModel.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(R.string.error_something_wrong, state.error?.title)
        assertEquals(R.string.error_something_wrong_description, state.error?.description)
        assertFalse(state.isLoading)
    }

    @Test
    fun `signIn sets loading true during operation`() = runTest {
        val result = Resource.Success(Unit)
        coEvery { signInUseCase(any(), any()) } coAnswers {
            assertTrue(viewModel.state.value.isLoading)
            result
        }

        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("12345678")
        viewModel.signIn()

        assertFalse(viewModel.state.value.isLoading)
    }
}