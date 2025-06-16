package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import ch.nicolaszurbuchen.socially.common.auth.domain.AuthRepository
import ch.nicolaszurbuchen.socially.common.utils.DomainError
import ch.nicolaszurbuchen.socially.common.utils.Resource
import ch.nicolaszurbuchen.socially.common.utils.isEmailValid

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
