package edu.ucne.keepfocus.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.keepfocus.data.repositories.FocusZoneRepositoryImpl
import edu.ucne.keepfocus.domain.repositories.AppRepository
import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import edu.ucne.keepfocus.domain.usecases.CreateFocusZone

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCreateFocusZone(
        repositoryImpl: FocusZoneRepositoryImpl
    ) = CreateFocusZone(repositoryImpl)
}