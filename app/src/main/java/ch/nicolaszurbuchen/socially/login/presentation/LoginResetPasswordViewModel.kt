package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthResetPasswordUseCase
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
): ViewModel() {

    private val _state = MutableStateFlow(LoginResetPasswordState())
    val state: StateFlow<LoginResetPasswordState>
        get() = _state

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun sendEmail() {
        val state = _state.value

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = resetPasswordUseCase(state.email)

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