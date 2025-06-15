package ch.nicolaszurbuchen.socially.login.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.components.model.SociallyErrorState

data class LoginSignUpState(
    val username: String = "",
    @StringRes val usernameError: Int? = null,
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: SociallyErrorState? = null,
) {

    val usernameIcon: Int
        @DrawableRes
        get() = if (username.isEmpty()) R.drawable.ic_user_off else R.drawable.ic_user_on

    val isUsernameError: Boolean
        get() = usernameError != null

    val emailIcon: Int
        @DrawableRes
        get() = if (email.isEmpty()) R.drawable.ic_email_off else R.drawable.ic_email_on

    val isEmailError: Boolean
        get() = emailError != null

    val passwordIcon: Int
        @DrawableRes
        get() = if (password.isEmpty()) R.drawable.ic_lock_off else R.drawable.ic_lock_on

    val isPasswordError: Boolean
        get() = passwordError != null

    val isSignUpButtonEnabled: Boolean
        get() = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && !isLoading
}
