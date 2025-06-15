package ch.nicolaszurbuchen.socially.common.auth.domain

import ch.nicolaszurbuchen.socially.utils.Resource
import ch.nicolaszurbuchen.socially.utils.ValidationErrors
import ch.nicolaszurbuchen.socially.utils.validate

class AuthSignUpUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(username: String, email: String, password: String): Resource<Unit> {
        val validationErrors = validate(username, email, password)

        if (validationErrors.isNotEmpty()) {
            return Resource.Failure(ValidationErrors(validationErrors))
        }

        return repository.signUp(username, email, password)
    }
}
