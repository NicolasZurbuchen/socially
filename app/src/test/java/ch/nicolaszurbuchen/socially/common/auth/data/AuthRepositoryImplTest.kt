package ch.nicolaszurbuchen.socially.common.auth.data

import ch.nicolaszurbuchen.socially.common.utils.Resource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var usersCollection: CollectionReference
    private lateinit var userDocument: DocumentReference
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        firebaseAuth = mockk(relaxed = true)
        firestore = mockk()
        usersCollection = mockk()
        userDocument = mockk()

        every { firestore.collection("users") } returns usersCollection
        every { usersCollection.document(any()) } returns userDocument

        repository = AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Test
    fun `signUp returns success when Firebase creates user`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val username = "testuser"
        val uid = "123456"

        val mockAuthResult = mockk<AuthResult> {
            every { user?.uid } returns uid
        }

        every {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        } returns Tasks.forResult(mockAuthResult)

        every {
            userDocument.set(any())
        } returns Tasks.forResult(null)

        val result = repository.signUp(username, email, password)
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `signUp returns error when Firebase throws`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val username = "testuser"

        every {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        } returns Tasks.forException(Exception())

        val result = repository.signUp(username, email, password)
        assertTrue(result is Resource.Failure)
    }

    @Test
    fun `signIn returns success when credentials are valid`() = runTest {
        val email = "test@example.com"
        val password = "password123"

        every {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        } returns Tasks.forResult(mockk<AuthResult>())

        val result = repository.signIn(email, password)
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `signIn returns error when Firebase fails`() = runTest {
        val email = "test@example.com"
        val password = "password123"

        every {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        } returns Tasks.forException(Exception())

        val result = repository.signIn(email, password)
        assertTrue(result is Resource.Failure)
    }

    @Test
    fun `resetPassword returns success when Firebase completes`() = runTest {
        val email = "test@example.com"

        every {
            firebaseAuth.sendPasswordResetEmail(email)
        } returns Tasks.forResult(null)

        val result = repository.resetPassword(email)
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `resetPassword returns error when Firebase throws`() = runTest {
        val email = "test@example.com"

        every {
            firebaseAuth.sendPasswordResetEmail(email)
        } returns Tasks.forException(Exception())

        val result = repository.resetPassword(email)
        assertTrue(result is Resource.Failure)
    }

    @Test
    fun `isUserSignedIn returns true when user is not null`() = runTest {
        every { firebaseAuth.currentUser } returns mockk<FirebaseUser>()

        val result = repository.isUserSignedIn()
        assertTrue(result)
    }

    @Test
    fun `isUserSignedIn returns false when user is null`() = runTest {
        every { firebaseAuth.currentUser } returns null

        val result = repository.isUserSignedIn()
        assertFalse(result)
    }

    @Test
    fun `signOut calls firebaseAuth signOut`() = runTest {
        every { firebaseAuth.signOut() } just Runs

        repository.signOut()

        verify(exactly = 1) { firebaseAuth.signOut() }
    }
}