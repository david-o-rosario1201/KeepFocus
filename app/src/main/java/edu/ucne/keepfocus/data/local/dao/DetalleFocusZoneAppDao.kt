package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.keepfocus.data.local.entities.DetalleFocusZoneAppEntity

@Dao
interface DetalleFocusZoneAppDao {
    @Upsert
    suspend fun upsertDetalle(detalle: DetalleFocusZoneAppEntity)

    @Query("""
        DELETE FROM DetalleFocusZoneApp
        WHERE focusZoneId = :focusZoneId
    """)
    suspend fun deleteByFocusZone(focusZoneId: Int)
}