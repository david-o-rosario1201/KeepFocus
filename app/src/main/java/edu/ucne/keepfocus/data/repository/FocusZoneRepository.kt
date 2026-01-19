package edu.ucne.keepfocus.data.repository

import edu.ucne.keepfocus.data.local.dao.FocusZoneDao
import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity
import javax.inject.Inject

class FocusZoneRepository @Inject constructor(
    private val focusZoneDao: FocusZoneDao
) {
    suspend fun upsertFocusZone(focusZone: FocusZoneEntity) = focusZoneDao.upsertFocusZone(focusZone)
    suspend fun getFocusZoneById(focusZoneId: Int) = focusZoneDao.getFocusZoneById(focusZoneId)
    suspend fun deleteFocusZone(focusZone: FocusZoneEntity) = focusZoneDao.deleteFocusZone(focusZone)
    suspend fun getFocusZones() = focusZoneDao.getFocusZones()
}