package ch.nicolaszurbuchen.socially.common.auth.domain

import ch.nicolaszurbuchen.socially.utils.Resource

interface AuthRepository {

    suspend fun isUserSignedIn(): Boolean

    suspend fun signUp(username: String, email: String, password: String): Resource<Unit>

    suspend fun signIn(email: String, password: String): Resource<Unit>

    suspend fun resetPassword(email: String): Resource<Unit>

    suspend fun signOut()
}