package ch.nicolaszurbuchen.socially.login.domain

class LoginSignUpUseCase(
    private val repository: LoginRepository,
) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<Unit> {
        return repository.signUp(username, email, password)
    }
}
