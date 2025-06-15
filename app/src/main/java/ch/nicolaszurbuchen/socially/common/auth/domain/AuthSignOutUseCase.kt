package ch.nicolaszurbuchen.socially.common.auth.domain

import javax.inject.Inject

class AuthSignOutUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() {
        return repository.signOut()
    }
}
