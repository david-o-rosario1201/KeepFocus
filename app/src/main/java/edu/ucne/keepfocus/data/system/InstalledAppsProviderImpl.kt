package edu.ucne.keepfocus.data.system

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.ucne.keepfocus.domain.models.App
import edu.ucne.keepfocus.domain.system.InstalledAppsProvider
import javax.inject.Inject

class InstalledAppsProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): InstalledAppsProvider {
    //@SuppressLint("QueryPermissionsNeeded")
    override fun getInstalledApps(): List<App> {
        val pm = context.packageManager

        return pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0}
            .map {
                App(
                    packageName = it.packageName,
                    name = pm.getApplicationLabel(it).toString(),
                    isBlocked = false,
                    dailyLimitMinutes = 0
                )
            }.sortedBy { it.name.lowercase() }
    }
}