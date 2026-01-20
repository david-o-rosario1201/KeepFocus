package edu.ucne.keepfocus.data.repository

import edu.ucne.keepfocus.data.local.dao.DetalleFocusZoneAppDao
import edu.ucne.keepfocus.data.local.entities.DetalleFocusZoneAppEntity
import javax.inject.Inject

class DetalleFocusZoneAppRepository @Inject constructor(
    private val detalleFocusZoneAppDao: DetalleFocusZoneAppDao
){
    suspend fun upsertDetalleFocusZoneApp(detalle: DetalleFocusZoneAppEntity) = detalleFocusZoneAppDao.upsertDetalle(detalle)
    suspend fun deleteDetalleFocusZoneApp(focusZoneId: Int) = detalleFocusZoneAppDao.deleteByFocusZone(focusZoneId)
}