package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import edu.ucne.keepfocus.data.local.entities.AppEntity
import edu.ucne.keepfocus.data.local.entities.DetalleFocusZoneAppEntity
import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity

@Dao
abstract class FocusZoneTransactionDao {
    @Insert
    abstract suspend fun insertFocusZone(focusZone: FocusZoneEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertApp(app: AppEntity)

    @Insert
    abstract suspend fun insertDetalle(detalle: DetalleFocusZoneAppEntity)

    @Transaction
    open suspend fun insertFocusZoneWithApps(
        focusZone: FocusZoneEntity,
        apps: List<AppEntity>
    ){
        val focusZoneId = insertFocusZone(focusZone).toInt()

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