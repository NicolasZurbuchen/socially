package ch.nicolaszurbuchen.socially.common.auth.domain

import ch.nicolaszurbuchen.socially.common.utils.DomainError
import ch.nicolaszurbuchen.socially.common.utils.FirebaseErrorType
import ch.nicolaszurbuchen.socially.common.utils.Resource

class AuthRepositoryFake : AuthRepository {

    var shouldFail = false
    var signedIn = false

    override suspend fun isUserSignedIn(): Boolean = signedIn

    override suspend fun signUp(username: String, email: String, password: String): Resource<Unit> {
        return if (shouldFail) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.NetworkError))
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun signIn(email: String, password: String): Resource<Unit> {
        return if (shouldFail) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.NetworkError))
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun resetPassword(email: String): Resource<Unit> {
        return if (shouldFail) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.NetworkError))
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun signOut() {
        signedIn = false
    }
}