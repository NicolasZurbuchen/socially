package ch.nicolaszurbuchen.socially

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthIsSignedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authIsSignedInUseCase: AuthIsSignedInUseCase,
): ViewModel() {

    var startDestination by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            val signedIn = authIsSignedInUseCase()
            startDestination = if (signedIn) Screen.TimelineHomeScreen.route else Screen.IntroWelcomeScreen.route
        }
    }
}