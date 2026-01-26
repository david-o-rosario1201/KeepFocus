package edu.ucne.keepfocus.data.repositories

import edu.ucne.keepfocus.data.local.dao.DetalleFocusZoneAppDao
import edu.ucne.keepfocus.data.mappers.asEntity
import edu.ucne.keepfocus.domain.models.DetalleFocusZoneApp
import edu.ucne.keepfocus.domain.repositories.DetalleFocusZoneAppRepository
import javax.inject.Inject

class DetalleFocusZoneAppRepositoryImpl @Inject constructor(
    private val detalleFocusZoneAppDao: DetalleFocusZoneAppDao
): DetalleFocusZoneAppRepository{
    override suspend fun upsertDetalleFocusZoneApp(detalle: DetalleFocusZoneApp) {
        return detalleFocusZoneAppDao.upsertDetalle(detalle.asEntity())
    }

    override suspend fun deleteDetalleFocusZoneApp(detalle: DetalleFocusZoneApp) {
        return detalleFocusZoneAppDao.deleteByFocusZone(detalle.focusZoneId)
    }
}