package edu.ucne.keepfocus.data.repository

import edu.ucne.keepfocus.data.local.dao.AppDao
import edu.ucne.keepfocus.data.local.entities.AppEntity
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDao: AppDao
) {
    suspend fun upsertApp(app: AppEntity) = appDao.upsertApp(app)
    suspend fun getAppById(appId: Int) = appDao.getAppById(appId)
    suspend fun deleteApp(app: AppEntity) = appDao.deleteApp(app)
    suspend fun getApps() = appDao.getApps()
}