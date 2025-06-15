package ch.nicolaszurbuchen.socially.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginSignUpState

@Composable
fun LoginSignUpScreen(
    navController: NavController,
    viewModel: LoginSignUpViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    if (state.success) {
        navController.navigate(Screen.TimelineHomeScreen.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    LoginSignUpScreenContent(
        state = state,
        onNavigateUp = { navController.navigateUp() },
        onUsernameValueChange = viewModel::updateUsername,
        onEmailValueChange = viewModel::updateEmail,
        onPasswordValueChange = viewModel::updatePassword,
        onPasswordVisibilityToggle = viewModel::togglePasswordVisibility,
        onSignUpClick = viewModel::signUp,
        onHaveAccountClick = { navController.navigate(Screen.LoginSignInScreen.route) },
    )
}

@Composable
fun LoginSignUpScreenContent(
    state: LoginSignUpState,
    onNavigateUp: () -> Unit,
    onUsernameValueChange: (String) -> Unit,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onSignUpClick: () -> Unit,
    onHaveAccountClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SociallyTopAppBar(
                onNavigateUp = onNavigateUp,
            )
        },
        modifier = Modifier
            .imePadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(R.dimen.padding_l)),
        ) {
            Text(
                text = stringResource(R.string.login_sign_up_title),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_xl))
            )

            SociallyTextField(
                value = state.username,
                onValueChange = onUsernameValueChange,
                placeholder = stringResource(R.string.login_username),
                leadingIcon = painterResource(state.usernameIcon),
                supportingText = state.usernameError,
                isError = state.isUsernameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_lh))
                    .height(dimensionResource(R.dimen.size_level_9))
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
                    .padding(top = dimensionResource(R.dimen.padding_s))
                    .height(dimensionResource(R.dimen.size_level_9))
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
                    .height(dimensionResource(R.dimen.size_level_9))
            )

            SociallyButtonPrimary(
                text = stringResource(R.string.login_sign_up),
                onClick = {
                    keyboardController?.hide()
                    onSignUpClick()
                },
                enabled = state.isSignUpButtonEnabled,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_s))
                    .height(dimensionResource(R.dimen.size_level_7))
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(R.dimen.padding_m),
                        bottom = dimensionResource(R.dimen.padding_l)
                    )
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.extraSmall,
                        )
                        .clickable { onHaveAccountClick() },
                ) {
                    Text(
                        text = stringResource(R.string.login_have_account),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = stringResource(R.string.login_sign_in),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_xs))
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
                            .padding(vertical = dimensionResource(R.dimen.padding_l)),
                    )
                }
            }
        }
    }
}