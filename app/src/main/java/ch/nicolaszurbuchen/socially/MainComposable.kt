package ch.nicolaszurbuchen.socially

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.nicolaszurbuchen.socially.login.presentation.LoginSignInScreen
import ch.nicolaszurbuchen.socially.login.presentation.LoginSignUpScreen

@Composable
fun MainComposable(
    navController: NavHostController = rememberNavController(),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.LoginSignInScreen.route,
        ) {
            composable(Screen.LoginSignInScreen.route) {
                LoginSignInScreen(
                    navController = navController,
                )
            }
            composable(Screen.LoginSignUpScreen.route) {
                LoginSignUpScreen(
                    navController = navController,
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object LoginSignInScreen : Screen("login_sign_in")
    data object LoginSignUpScreen : Screen("login_sign_up")
}