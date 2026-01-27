package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.App
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun upsertApp(app: App)
    fun observeAppById(appId: Int): Flow<App?>
    suspend fun deleteApp(app: App)
    fun observeApps(): Flow<List<App>>
}