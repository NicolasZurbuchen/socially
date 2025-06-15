package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthSignUpUseCase
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(
    private val signUpUseCase: AuthSignUpUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(LoginSignUpState())
    val state: StateFlow<LoginSignUpState>
        get() = _state

    fun updateUsername(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _state.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun signUp() {
        val state = _state.value

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = signUpUseCase(state.username, state.email, state.password)

            _state.update {
                if (result.isSuccess) {
                    it.copy(isLoading = false, success = true)
                } else {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}