package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.App

interface AppsRepository {
    fun getInstalledApps(): List<App>
}