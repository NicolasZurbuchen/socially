package ch.nicolaszurbuchen.socially.login.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.components.model.SociallyErrorState

data class LoginResetPasswordState(
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: SociallyErrorState? = null,
) {

    val emailIcon: Int
        @DrawableRes
        get() = if (email.isEmpty()) R.drawable.ic_email_off else R.drawable.ic_email_on

    val isEmailError: Boolean
        get() = emailError != null

    val isSignInButtonEnabled: Boolean
        get() = email.isNotEmpty() && !isLoading
}
