package edu.ucne.keepfocus.domain.system

import edu.ucne.keepfocus.domain.models.App

interface InstalledAppsProvider {
    fun getInstalledApps(): List<App>
}