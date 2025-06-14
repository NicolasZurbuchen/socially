package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignInState
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(

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
        _state.update { it.copy(isLoading = true) }

        // TODO
    }
}