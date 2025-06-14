package ch.nicolaszurbuchen.socially.login.presentation.model

import androidx.annotation.DrawableRes
import ch.nicolaszurbuchen.socially.R

data class LoginSignUpState(
    val username: String = "",
    val usernameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val success: Boolean = false,
) {

    val usernameIcon: Int
        @DrawableRes
        get() = if (username.isEmpty()) R.drawable.ic_user_off else R.drawable.ic_user_on

    val isUsernameError: Boolean
        get() = !usernameError.isNullOrEmpty()

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

    val isSignUpButtonEnabled: Boolean
        get() = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && !isLoading
}
