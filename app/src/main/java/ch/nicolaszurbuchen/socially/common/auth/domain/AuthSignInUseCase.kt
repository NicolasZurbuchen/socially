package ch.nicolaszurbuchen.socially.common.auth.domain

class AuthSignInUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.signIn(email, password)
    }
}
