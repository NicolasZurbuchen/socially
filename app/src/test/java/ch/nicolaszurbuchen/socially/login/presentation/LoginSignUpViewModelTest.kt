package ch.nicolaszurbuchen.socially.login.presentation

import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.auth.domain.use_case.AuthSignUpUseCase
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

class LoginSignUpViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: LoginSignUpViewModel
    private val signUpUseCase = mockk<AuthSignUpUseCase>()

    @Before
    fun setup() {
        viewModel = LoginSignUpViewModel(signUpUseCase)
    }

    @Test
    fun `updateUsername updates state correctly`() = runTest {
        viewModel.updateUsername("testuser")
        assertEquals("testuser", viewModel.state.value.username)
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
    fun `togglePasswordVisibility toggles correctly`() = runTest {
        val initial = viewModel.state.value.passwordVisible
        viewModel.togglePasswordVisibility()
        assertEquals(!initial, viewModel.state.value.passwordVisible)
    }

    @Test
    fun `signUp sets loading true then success`() = runTest {
        coEvery { signUpUseCase("testuser", "test@example.com", "123456") } returns Resource.Success(Unit)

        viewModel.updateUsername("testuser")
        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("123456")
        viewModel.signUp()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.usernameError)
        assertNull(state.emailError)
        assertNull(state.passwordError)
        assertNull(state.error)
    }

    @Test
    fun `signUp shows validation errors`() = runTest {
        coEvery {
            signUpUseCase("bad", "invalid", "123")
        } returns Resource.Failure(
            ValidationErrors(listOf(
                FieldValidationError(Field.USERNAME, DomainError.InvalidUsername),
                FieldValidationError(Field.EMAIL, DomainError.InvalidEmail),
                FieldValidationError(Field.PASSWORD, DomainError.InvalidPassword)
            ))
        )

        viewModel.updateUsername("bad")
        viewModel.updateEmail("invalid")
        viewModel.updatePassword("123")
        viewModel.signUp()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(R.string.error_invalid_username, state.usernameError)
        assertEquals(R.string.error_invalid_email, state.emailError)
        assertEquals(R.string.error_invalid_password, state.passwordError)
        assertFalse(state.isLoading)
    }

    @Test
    fun `signUp shows firebase error`() = runTest {
        coEvery {
            signUpUseCase(any(), any(), any())
        } returns Resource.Failure(DomainError.Firebase(FirebaseErrorType.Unknown))

        viewModel.updateUsername("testuser")
        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("123456")
        viewModel.signUp()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(R.string.error_something_wrong, state.error?.title)
        assertEquals(R.string.error_something_wrong_description, state.error?.description)
        assertFalse(state.isLoading)
    }
}
