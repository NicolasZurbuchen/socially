package ch.nicolaszurbuchen.socially.login.di

import ch.nicolaszurbuchen.socially.login.data.LoginRepositoryImpl
import ch.nicolaszurbuchen.socially.login.domain.LoginRepository
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
    fun provideDashboardRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }
}