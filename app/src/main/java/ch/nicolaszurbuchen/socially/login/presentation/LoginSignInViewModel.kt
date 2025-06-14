package ch.nicolaszurbuchen.socially.login.presentation

import androidx.lifecycle.ViewModel
import ch.nicolaszurbuchen.socially.login.domain.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginSignInViewModel @Inject constructor(
    private val repository: LoginRepository,
): ViewModel() {
}