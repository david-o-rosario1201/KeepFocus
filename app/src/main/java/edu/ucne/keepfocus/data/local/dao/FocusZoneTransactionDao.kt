package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import edu.ucne.keepfocus.data.local.entities.AppEntity
import edu.ucne.keepfocus.data.local.entities.DetalleFocusZoneAppEntity
import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity

@Dao
abstract class FocusZoneTransactionDao {
    @Upsert
    abstract suspend fun upsertFocusZone(focusZone: FocusZoneEntity): Long

    @Upsert
    abstract suspend fun insertApp(app: AppEntity)

    @Insert
    abstract suspend fun insertDetalle(detalle: DetalleFocusZoneAppEntity)

    @Query("DELETE FROM DetalleFocusZoneApp WHERE focusZoneId = :focusZoneId")
    abstract suspend fun deleteDetallesByFocusId(focusZoneId: Int)

    @Transaction
    open suspend fun insertFocusZoneWithApps(
        focusZone: FocusZoneEntity,
        apps: List<AppEntity>
    ){
        val result = upsertFocusZone(focusZone)

        val focusZoneId = if (focusZone.focusZoneId == 0) {
            result.toInt()
        } else {
            focusZone.focusZoneId
        }

        deleteDetallesByFocusId(focusZoneId)

        apps.forEach{ app ->
            // 1. Guardar app si no existe
            insertApp(app)

            // 2. Crear relación
            insertDetalle(
                DetalleFocusZoneAppEntity(
                    focusZoneId = focusZoneId,
                    packageName = app.packageName
                )
            )
        }
    }
}