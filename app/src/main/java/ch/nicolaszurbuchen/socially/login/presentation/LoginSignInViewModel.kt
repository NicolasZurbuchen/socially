package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.login.domain.LoginRepository
import ch.nicolaszurbuchen.socially.login.domain.LoginSignInUseCase
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignInViewModel @Inject constructor(
    private val signInUseCase: LoginSignInUseCase,
): ViewModel() {

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
            _state.update { it.copy(isLoading = true) }

            val result = signInUseCase(state.email, state.password)

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