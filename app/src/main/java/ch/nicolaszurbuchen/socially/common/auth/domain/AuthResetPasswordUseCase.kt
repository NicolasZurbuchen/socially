package ch.nicolaszurbuchen.socially.common.auth.domain

class AuthResetPasswordUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.resetPassword(email)
    }
}
