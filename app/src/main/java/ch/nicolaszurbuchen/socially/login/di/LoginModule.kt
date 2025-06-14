package ch.nicolaszurbuchen.socially.login.di

import ch.nicolaszurbuchen.socially.login.data.LoginRepositoryImpl
import ch.nicolaszurbuchen.socially.login.domain.LoginRepository
import ch.nicolaszurbuchen.socially.login.domain.LoginResetPasswordUseCase
import ch.nicolaszurbuchen.socially.login.domain.LoginSignInUseCase
import ch.nicolaszurbuchen.socially.login.domain.LoginSignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): LoginRepository {
        return LoginRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideLoginSignUpUseCase(
        repository: LoginRepository,
    ): LoginSignUpUseCase {
        return LoginSignUpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginSignInUseCase(
        repository: LoginRepository,
    ): LoginSignInUseCase {
        return LoginSignInUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginResetPasswordUseCase(
        repository: LoginRepository,
    ): LoginResetPasswordUseCase {
        return LoginResetPasswordUseCase(repository)
    }
}