package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.auth.domain.use_case.AuthSignInUseCase
import ch.nicolaszurbuchen.socially.common.components.model.SociallyErrorState
import ch.nicolaszurbuchen.socially.common.utils.Field
import ch.nicolaszurbuchen.socially.common.utils.Resource
import ch.nicolaszurbuchen.socially.common.utils.ValidationErrors
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignInViewModel @Inject constructor(
    private val signInUseCase: AuthSignInUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Unit>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = MutableStateFlow(LoginSignInState())
    val state: StateFlow<LoginSignInState>
        get() = _state

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _state.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun signIn() {
        val state = _state.value

        viewModelScope.launch {
            _state.update {
                it.copy(
                    emailError = null,
                    passwordError = null,
                    isLoading = true,
                    error = null,
                )
            }

            when (val result = signInUseCase(state.email, state.password)) {
                is Resource.Success -> {
                    _eventFlow.emit(Unit)
                    _state.update { it.copy(isLoading = false) }
                }

                is Resource.Failure -> {
                    if (result.error is ValidationErrors) {
                        result.error.errors.forEach { field ->
                            when (field.field) {
                                Field.EMAIL -> _state.update {
                                    it.copy(
                                        emailError = R.string.error_invalid_email,
                                        isLoading = false,
                                    )
                                }

                                Field.PASSWORD -> _state.update {
                                    it.copy(
                                        passwordError = R.string.error_invalid_password,
                                        isLoading = false,
                                    )
                                }

                                Field.USERNAME -> {}
                            }
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = SociallyErrorState(
                                    title = R.string.error_something_wrong,
                                    description = R.string.error_something_wrong_description,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}