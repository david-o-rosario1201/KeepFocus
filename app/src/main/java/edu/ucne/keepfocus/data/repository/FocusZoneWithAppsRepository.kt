package edu.ucne.keepfocus.data.repository

import edu.ucne.keepfocus.data.local.dao.FocusZoneWithAppsDao
import javax.inject.Inject

class FocusZoneWithAppsRepository @Inject constructor(
    private val focusZoneWithAppsDao: FocusZoneWithAppsDao
) {
    suspend fun getFocusZoneWithApps(focusZoneId: Int) = focusZoneWithAppsDao.getFocusZoneWithApps(focusZoneId)
    suspend fun getFocusZoneWithApps() = focusZoneWithAppsDao.getFocusZoneWithApps()
}