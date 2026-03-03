package edu.ucne.keepfocus.data.repositories

import edu.ucne.keepfocus.domain.models.App
import edu.ucne.keepfocus.domain.repositories.AppsRepository
import edu.ucne.keepfocus.domain.system.InstalledAppsProvider
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val installedAppsProvider: InstalledAppsProvider
): AppsRepository {
    override fun getInstalledApps(): List<App> {
        return installedAppsProvider.getInstalledApps()
    }
}