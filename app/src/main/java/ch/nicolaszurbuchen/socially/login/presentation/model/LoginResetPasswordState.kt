package ch.nicolaszurbuchen.socially.login.presentation.model

import androidx.annotation.DrawableRes
import ch.nicolaszurbuchen.socially.R

data class LoginResetPasswordState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
) {

    val emailIcon: Int
        @DrawableRes
        get() = if (email.isEmpty()) R.drawable.ic_email_off else R.drawable.ic_email_on

    val isEmailError: Boolean
        get() = !emailError.isNullOrEmpty()

    val isSignInButtonEnabled: Boolean
        get() = email.isNotEmpty() && !isLoading
}
