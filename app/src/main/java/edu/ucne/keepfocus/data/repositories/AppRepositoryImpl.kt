package edu.ucne.keepfocus.data.repositories

import edu.ucne.keepfocus.data.local.dao.AppDao
import edu.ucne.keepfocus.data.mappers.asEntity
import edu.ucne.keepfocus.data.mappers.toDomain
import edu.ucne.keepfocus.domain.models.App
import edu.ucne.keepfocus.domain.repositories.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appDao: AppDao
): AppRepository {
    override suspend fun upsertApp(app: App) {
        return appDao.upsertApp(app.asEntity())
    }

    override fun getAppById(appId: Int): Flow<App>? {
        return appDao.getAppById(appId)
            ?.map { app ->
                app.toDomain()
            }
    }

    override suspend fun deleteApp(app: App) {
        return appDao.deleteApp(app.asEntity())
    }

    override fun getApps(): Flow<List<App>> {
        return appDao.getApps()
            .map { apps ->
                apps.map { it.toDomain() }
            }
    }
}