package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import ch.nicolaszurbuchen.socially.common.auth.domain.AuthRepository
import ch.nicolaszurbuchen.socially.common.utils.Resource
import ch.nicolaszurbuchen.socially.common.utils.ValidationErrors
import ch.nicolaszurbuchen.socially.common.utils.validate

class AuthSignInUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        val validationErrors = validate(email, password)

        if (validationErrors.isNotEmpty()) {
            return Resource.Failure(ValidationErrors(validationErrors))
        }

        return repository.signIn(email, password)
    }
}
