package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusZoneDao {
    @Upsert
    suspend fun upsertFocusZone(focusZone: FocusZoneEntity)

    @Query("""
        SELECT * FROM FocusZones
        WHERE focusZoneId = :focusZoneId
        LIMIT 1
    """)
    suspend fun getFocusZoneById(focusZoneId: Int): Flow<FocusZoneEntity>?

    @Delete
    suspend fun deleteFocusZone(focusZone: FocusZoneEntity)

    @Query("SELECT * FROM FocusZones")
    suspend fun getFocusZones(): Flow<List<FocusZoneEntity>>
}