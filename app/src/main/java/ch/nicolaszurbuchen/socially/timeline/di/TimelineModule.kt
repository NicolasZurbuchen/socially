package ch.nicolaszurbuchen.socially.timeline.di

import ch.nicolaszurbuchen.socially.timeline.data.TimelineRepositoryImpl
import ch.nicolaszurbuchen.socially.timeline.domain.use_case.TimelineCreateNewPostUseCase
import ch.nicolaszurbuchen.socially.timeline.domain.use_case.TimelineGetPostsUseCase
import ch.nicolaszurbuchen.socially.timeline.domain.TimelineRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimelineModule {

    @Provides
    @Singleton
    fun provideTimelineRepository(
        auth: FirebaseAuth,
        storage: FirebaseStorage,
        firestore: FirebaseFirestore,
    ): TimelineRepository {
        return TimelineRepositoryImpl(auth, storage, firestore)
    }

    @Provides
    @Singleton
    fun provideTimelineCreateNewPostUseCase(
        repository: TimelineRepository,
    ): TimelineCreateNewPostUseCase {
        return TimelineCreateNewPostUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTimelineGetPostsUseCase(
        repository: TimelineRepository,
    ): TimelineGetPostsUseCase {
        return TimelineGetPostsUseCase(repository)
    }
}