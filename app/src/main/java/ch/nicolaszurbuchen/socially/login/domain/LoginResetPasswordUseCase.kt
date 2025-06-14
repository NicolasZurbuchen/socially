package ch.nicolaszurbuchen.socially.login.domain

class LoginResetPasswordUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.resetPassword(email)
    }
}
