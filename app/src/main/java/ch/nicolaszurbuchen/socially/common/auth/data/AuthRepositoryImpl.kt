package ch.nicolaszurbuchen.socially.common.auth.data

import ch.nicolaszurbuchen.socially.utils.DomainError
import ch.nicolaszurbuchen.socially.utils.FirebaseErrorType
import ch.nicolaszurbuchen.socially.utils.Resource
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
): AuthRepository {

    override suspend fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun signUp(username: String, email: String, password: String): Resource<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User ID not found")

            val userData = hashMapOf(
                "username" to username,
                "email" to email
            )

            firestore.collection("users").document(uid).set(userData).await()
            Resource.Success(Unit)
        } catch (e: FirebaseNetworkException) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.NetworkError))
        } catch (e: Exception) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.Unknown))
        }
    }

    override suspend fun signIn(email: String, password: String): Resource<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(Unit)
        } catch (e: FirebaseNetworkException) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.NetworkError))
        } catch (e: Exception) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.Unknown))
        }
    }

    override suspend fun resetPassword(email: String): Resource<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resource.Success(Unit)
        } catch (e: FirebaseNetworkException) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.NetworkError))
        } catch (e: Exception) {
            Resource.Failure(DomainError.Firebase(FirebaseErrorType.Unknown))
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}