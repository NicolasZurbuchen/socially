package ch.nicolaszurbuchen.socially.intro.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.Screen
import ch.nicolaszurbuchen.socially.common.ui.SociallyElevatedButton
import ch.nicolaszurbuchen.socially.ui.theme.SociallyTheme

@Composable
fun IntroWelcomeScreen(
    navController: NavController,
) {
    IntroWelcomeScreenContent(
        onContinueClick = { navController.navigate(Screen.LoginSignUpScreen.route) },
    )
}

@Composable
fun IntroWelcomeScreenContent(
    onContinueClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_welcome),
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(R.dimen.padding_l),
                    top = dimensionResource(R.dimen.padding_xxl),
                    bottom = dimensionResource(R.dimen.padding_xl),
                    end = dimensionResource(R.dimen.padding_l),
                )
        ) {
            Text(
                text = stringResource(R.string.intro_welcome_title),
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = stringResource(R.string.intro_welcome_subtitle),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_m))
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            SociallyElevatedButton(
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                onClick = onContinueClick,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(dimensionResource(R.dimen.size_level_7))
            )
        }
    }
}

@Preview
@Composable
private fun IntroWelcomeScreenContentPreview() {
    SociallyTheme {
        IntroWelcomeScreenContent(
            onContinueClick = {},
        )
    }
}