package ch.nicolaszurbuchen.socially.login.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ch.nicolaszurbuchen.socially.login.presentation.model.LoginResetPasswordState

@Composable
fun LoginResetPasswordScreen(
    navController: NavController,
    viewModel: LoginResetPasswordViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val navigateToSignIn = {
        navController.navigate(Screen.LoginSignInScreen.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    LoginResetPasswordScreenContent(
        state = state,
        onNavigateUp = { navController.navigateUp() },
        onEmailValueChange = viewModel::updateEmail,
        onSendClick = viewModel::sendEmail,
    )

    if (state.success) {
        AlertDialog(
            onDismissRequest = navigateToSignIn,
            confirmButton = {
                TextButton(onClick = navigateToSignIn) {
                    Text(stringResource(R.string.common_ok))
                }
            },
            title = { Text(stringResource(R.string.login_password_reset_confirmation_title)) },
            text = { Text(stringResource(R.string.login_password_reset_confirmation)) }
        )
    }
}

@Composable
fun LoginResetPasswordScreenContent(
    state: LoginResetPasswordState,
    onNavigateUp: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            SociallyTopAppBar(
                onNavigateUp = onNavigateUp,
            )
        },
        modifier = Modifier
            .imePadding(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(R.dimen.padding_l)),
        ) {
            Text(
                text = stringResource(R.string.login_sign_in_title),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_xl)),
            )
            Text(
                text = stringResource(R.string.login_password_reset_description),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_m)),
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
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_lh))
                    .height(dimensionResource(R.dimen.size_level_9)),
            )

            SociallyButtonPrimary(
                text = stringResource(R.string.login_sign_in),
                onClick = {
                    keyboardController?.hide()
                    onSendClick()
                },
                enabled = state.isSignInButtonEnabled,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(R.dimen.padding_s),
                        bottom = dimensionResource(R.dimen.padding_l),
                    )
                    .height(dimensionResource(R.dimen.size_level_7)),
            )

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