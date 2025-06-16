package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.auth.domain.use_case.AuthResetPasswordUseCase
import ch.nicolaszurbuchen.socially.common.components.model.SociallyErrorState
import ch.nicolaszurbuchen.socially.common.utils.DomainError
import ch.nicolaszurbuchen.socially.common.utils.Resource
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: AuthResetPasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginResetPasswordState())
    val state: StateFlow<LoginResetPasswordState>
        get() = _state

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun sendEmail() {
        val state = _state.value

        viewModelScope.launch {
            _state.update { it.copy(emailError = null, isLoading = true, error = null) }

            when (val result = resetPasswordUseCase(state.email)) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, success = true) }
                }

                is Resource.Failure -> {
                    when (result.error) {
                        DomainError.InvalidEmail -> _state.update {
                            it.copy(
                                emailError = R.string.error_invalid_email,
                                isLoading = false,
                            )
                        }

                        is DomainError.Firebase -> _state.update {
                            it.copy(
                                isLoading = false,
                                error = SociallyErrorState(
                                    title = R.string.error_something_wrong,
                                    description = R.string.error_something_wrong_description,
                                )
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}