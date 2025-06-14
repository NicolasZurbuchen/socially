package ch.nicolaszurbuchen.socially.login.presentation.model

import androidx.annotation.DrawableRes
import ch.nicolaszurbuchen.socially.R

data class LoginSignInState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean = false,
) {

    val emailIcon: Int
        @DrawableRes
        get() = if (email.isEmpty()) R.drawable.ic_email_off else R.drawable.ic_email_on

    val isEmailError: Boolean
        get() = !emailError.isNullOrEmpty()

    val passwordIcon: Int
        @DrawableRes
        get() = if (password.isEmpty()) R.drawable.ic_lock_off else R.drawable.ic_lock_on

    val isPasswordError: Boolean
        get() = !passwordError.isNullOrEmpty()

    val isSignInButtonEnabled: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty() && !isLoading
}
