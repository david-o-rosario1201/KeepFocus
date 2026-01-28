package edu.ucne.keepfocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.keepfocus.data.system.AppUsageSystemImpl
import edu.ucne.keepfocus.domain.system.AppUsageSystem

@Module
@InstallIn(SingletonComponent::class)
abstract class SystemModule {

    @Binds
    abstract fun bindsAppUsageSystem(impl: AppUsageSystemImpl): AppUsageSystem

//    @Binds
//    abstract fun bindsInstalledAppsProvider(impl: InstalledAppsProviderImpl): InstalledAppsProvider
}