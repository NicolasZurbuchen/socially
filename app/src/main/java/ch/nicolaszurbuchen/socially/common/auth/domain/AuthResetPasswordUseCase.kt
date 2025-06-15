package ch.nicolaszurbuchen.socially.common.auth.domain

import ch.nicolaszurbuchen.socially.utils.DomainError
import ch.nicolaszurbuchen.socially.utils.Resource
import ch.nicolaszurbuchen.socially.utils.isEmailValid

class AuthResetPasswordUseCase(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(email: String): Resource<Unit> {
        if (!isEmailValid(email)) {
            return Resource.Failure(DomainError.InvalidEmail)
        }

        return repository.resetPassword(email)
    }
}
