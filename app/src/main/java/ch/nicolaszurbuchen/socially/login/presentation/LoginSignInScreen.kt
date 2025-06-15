package ch.nicolaszurbuchen.socially.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.Screen
import ch.nicolaszurbuchen.socially.common.ui.SociallyButtonPrimary
import ch.nicolaszurbuchen.socially.common.ui.SociallyTextField
import ch.nicolaszurbuchen.socially.common.ui.SociallyTopAppBar
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignInState

@Composable
fun LoginSignInScreen(
    navController: NavController,
    viewModel: LoginSignInViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    if (state.success) {
        navController.navigate(Screen.TimelineHomeScreen.route)
    }

    LoginSignInScreenContent(
        state = state,
        onNavigateUp = { navController.navigateUp() },
        onEmailValueChange = viewModel::updateEmail,
        onPasswordValueChange = viewModel::updatePassword,
        onPasswordVisibilityToggle = viewModel::togglePasswordVisibility,
        onForgetPasswordClick = { navController.navigate(Screen.LoginResetPassword.route) },
        onSignInClick = viewModel::signIn,
        onCreateAccountClick = {
            navController.navigate(Screen.LoginSignUpScreen.route) {
                popUpTo(Screen.LoginSignInScreen.route) { inclusive = true }
            }
        },
    )
}

@Composable
fun LoginSignInScreenContent(
    state: LoginSignInState,
    onNavigateUp: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onForgetPasswordClick: () -> Unit,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SociallyTopAppBar(
                onNavigateUp = onNavigateUp,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(R.dimen.padding_l)),
        ) {
            Text(
                text = stringResource(R.string.login_sign_in_title),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_xl)),
            )

            SociallyTextField(
                value = state.email,
                onValueChange = onEmailValueChange,
                placeholder = stringResource(R.string.login_email),
                leadingIcon = painterResource(state.emailIcon),
                supportingText = state.emailError,
                isError = state.isEmailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_lh))
                    .height(dimensionResource(R.dimen.socially_text_field_height)),
            )

            SociallyTextField(
                value = state.password,
                onValueChange = onPasswordValueChange,
                placeholder = stringResource(R.string.login_password),
                leadingIcon = painterResource(state.passwordIcon),
                supportingText = state.passwordError,
                isError = state.isPasswordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                isPassword = true,
                passwordVisible = state.passwordVisible,
                onPasswordVisibilityToggle = onPasswordVisibilityToggle,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_s))
                    .height(dimensionResource(R.dimen.socially_text_field_height)),
            )

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.login_forgot_password),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.extraSmall,
                        )
                        .clickable { onForgetPasswordClick() },
                )
            }

            SociallyButtonPrimary(
                text = stringResource(R.string.login_sign_in),
                onClick = {
                    keyboardController?.hide()
                    onSignInClick()
                },
                enabled = state.isSignInButtonEnabled,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_l))
                    .height(dimensionResource(R.dimen.socially_button_height)),
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_m))
                    .fillMaxWidth(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.extraSmall,
                        )
                        .clickable { onCreateAccountClick() },
                ) {
                    Text(
                        text = stringResource(R.string.login_create_account),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = stringResource(R.string.login_sign_up),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_xs)),
                    )
                }
            }

            if (state.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.padding_l)),
                    )
                }
            }
        }
    }
}
