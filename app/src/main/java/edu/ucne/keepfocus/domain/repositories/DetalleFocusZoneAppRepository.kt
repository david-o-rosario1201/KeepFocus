package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.DetalleFocusZoneApp

interface DetalleFocusZoneAppRepository {
    suspend fun upsertDetalleFocusZoneApp(detalle: DetalleFocusZoneApp)
    suspend fun deleteDetalleFocusZoneApp(detalle: DetalleFocusZoneApp)
}