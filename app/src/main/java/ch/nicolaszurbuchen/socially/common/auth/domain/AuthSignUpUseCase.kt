package ch.nicolaszurbuchen.socially.common.auth.domain

class AuthSignUpUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<Unit> {
        return repository.signUp(username, email, password)
    }
}
