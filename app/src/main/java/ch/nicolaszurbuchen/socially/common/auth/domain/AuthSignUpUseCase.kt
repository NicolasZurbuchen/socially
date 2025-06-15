package ch.nicolaszurbuchen.socially.common.auth.domain

import ch.nicolaszurbuchen.socially.common.utils.Resource
import ch.nicolaszurbuchen.socially.common.utils.ValidationErrors
import ch.nicolaszurbuchen.socially.common.utils.validate

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
