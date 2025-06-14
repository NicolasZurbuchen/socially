package ch.nicolaszurbuchen.socially.login.domain

class LoginSignInUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.signIn(email, password)
    }
}
