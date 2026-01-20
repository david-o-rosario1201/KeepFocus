package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.App
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun upsertApp(app: App)
    suspend fun getAppById(appId: Int): Flow<App>?
    suspend fun deleteApp(app: App)
    suspend fun getApps(): Flow<List<App>>
}