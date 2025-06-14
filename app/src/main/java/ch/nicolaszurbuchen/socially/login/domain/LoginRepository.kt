package ch.nicolaszurbuchen.socially.login.domain

interface LoginRepository {

    suspend fun signUp(username: String, email: String, password: String): Result<Unit>

    suspend fun signIn(email: String, password: String): Result<Unit>
}