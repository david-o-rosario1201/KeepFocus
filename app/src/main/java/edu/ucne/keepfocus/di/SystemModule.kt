package edu.ucne.keepfocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.keepfocus.data.system.AppIconProviderImpl
import edu.ucne.keepfocus.data.system.AppUsageSystemImpl
import edu.ucne.keepfocus.data.system.InstalledAppsProviderImpl
import edu.ucne.keepfocus.domain.system.AppIconProvider
import edu.ucne.keepfocus.domain.system.AppUsageSystem
import edu.ucne.keepfocus.domain.system.InstalledAppsProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class SystemModule {

    @Binds
    abstract fun bindsAppUsageSystem(impl: AppUsageSystemImpl): AppUsageSystem

    @Binds
    abstract fun bindsInstalledAppsProvider(impl: InstalledAppsProviderImpl): InstalledAppsProvider

    @Binds
    abstract fun bindsAppIconProvider(impl: AppIconProviderImpl): AppIconProvider
}