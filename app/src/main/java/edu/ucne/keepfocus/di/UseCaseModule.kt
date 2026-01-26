package edu.ucne.keepfocus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.keepfocus.data.repositories.FocusZoneRepositoryImpl
import edu.ucne.keepfocus.domain.usecases.UpsertFocusZone

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCreateFocusZone(
        repositoryImpl: FocusZoneRepositoryImpl
    ) = UpsertFocusZone(repositoryImpl)
}