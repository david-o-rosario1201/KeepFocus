package edu.ucne.keepfocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.keepfocus.data.repositories.AppRepositoryImpl
import edu.ucne.keepfocus.data.repositories.DetalleFocusZoneAppRepositoryImpl
import edu.ucne.keepfocus.data.repositories.FocusZoneRepositoryImpl
import edu.ucne.keepfocus.domain.repositories.AppRepository
import edu.ucne.keepfocus.domain.repositories.DetalleFocusZoneAppRepository
import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsAppRepository(impl: AppRepositoryImpl): AppRepository

    @Binds
    abstract fun bindsFocusRepository(impl: FocusZoneRepositoryImpl): FocusZoneRepository

    @Binds
    abstract fun bindsDetalleFocusZoneAppRepository(impl: DetalleFocusZoneAppRepositoryImpl): DetalleFocusZoneAppRepository
}