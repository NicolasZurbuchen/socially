package ch.nicolaszurbuchen.socially.common.auth.domain

interface AuthRepository {

    suspend fun isUserSignedIn(): Boolean

    suspend fun signUp(username: String, email: String, password: String): Result<Unit>

    suspend fun signIn(email: String, password: String): Result<Unit>

    suspend fun resetPassword(email: String): Result<Unit>

    suspend fun signOut()
}