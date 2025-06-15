package ch.nicolaszurbuchen.socially

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.nicolaszurbuchen.socially.intro.presentation.IntroWelcomeScreen
import ch.nicolaszurbuchen.socially.login.presentation.LoginResetPasswordScreen
import ch.nicolaszurbuchen.socially.login.presentation.LoginSignInScreen
import ch.nicolaszurbuchen.socially.login.presentation.LoginSignUpScreen
import ch.nicolaszurbuchen.socially.timeline.presentation.TimelineHomeScreen
import ch.nicolaszurbuchen.socially.timeline.presentation.TimelineNewPostScreen

@Composable
fun MainComposable(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val startDestination = viewModel.startDestination

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (startDestination != null) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                composable(Screen.IntroWelcomeScreen.route) {
                    IntroWelcomeScreen(
                        navController = navController,
                    )
                }
                composable(Screen.LoginSignUpScreen.route) {
                    LoginSignUpScreen(
                        navController = navController,
                    )
                }
                composable(Screen.LoginSignInScreen.route) {
                    LoginSignInScreen(
                        navController = navController,
                    )
                }
                composable(Screen.LoginResetPasswordScreen.route) {
                    LoginResetPasswordScreen(
                        navController = navController,
                    )
                }
                composable(Screen.TimelineHomeScreen.route) {
                    TimelineHomeScreen(
                        navController = navController,
                    )
                }
                composable(Screen.TimelineNewPostScreen.route) {
                    TimelineNewPostScreen(
                        navController = navController,
                    )
                }

                // TODO remember login
                // TODO event pour naviguer
                // TODO automatic focus
                // TODO field validation
                // TODO error management
                // TODO tests
                // TODO lock orientation
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object IntroWelcomeScreen : Screen("intro_welcome")
    data object LoginSignInScreen : Screen("login_sign_in")
    data object LoginSignUpScreen : Screen("login_sign_up")
    data object LoginResetPasswordScreen : Screen("login_password_reset")
    data object TimelineHomeScreen : Screen("timeline_home")
    data object TimelineNewPostScreen : Screen("timeline_new_post")
}