package ch.nicolaszurbuchen.socially.common.auth.domain

import javax.inject.Inject

class AuthIsSignedInUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean {
        return repository.isUserSignedIn()
    }
}
