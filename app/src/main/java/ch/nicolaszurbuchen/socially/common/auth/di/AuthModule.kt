package ch.nicolaszurbuchen.socially.common.auth.di

import ch.nicolaszurbuchen.socially.common.auth.data.AuthRepositoryImpl
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthIsSignedInUseCase
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthRepository
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthResetPasswordUseCase
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthSignInUseCase
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthSignOutUseCase
import ch.nicolaszurbuchen.socially.common.auth.domain.AuthSignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideAuthSignUpUseCase(
        repository: AuthRepository,
    ): AuthSignUpUseCase {
        return AuthSignUpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthSignInUseCase(
        repository: AuthRepository,
    ): AuthSignInUseCase {
        return AuthSignInUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthResetPasswordUseCase(
        repository: AuthRepository,
    ): AuthResetPasswordUseCase {
        return AuthResetPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthSignOutUseCase(
        repository: AuthRepository,
    ): AuthSignOutUseCase {
        return AuthSignOutUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthIsSignedInUseCase(
        repository: AuthRepository,
    ): AuthIsSignedInUseCase {
        return AuthIsSignedInUseCase(repository)
    }
}