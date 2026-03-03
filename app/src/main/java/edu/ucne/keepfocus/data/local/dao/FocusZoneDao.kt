package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import edu.ucne.keepfocus.data.local.entities.FocusZoneEntity
import edu.ucne.keepfocus.data.local.entities.FocusZoneWithAppsEntity
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
    fun observeFocusZoneById(focusZoneId: Int): Flow<FocusZoneEntity?>

    @Delete
    suspend fun deleteFocusZone(focusZone: FocusZoneEntity)

    @Query("SELECT * FROM FocusZones")
    fun observeFocusZones(): Flow<List<FocusZoneEntity>>

    @Transaction
    @Query("""
        SELECT * FROM FocusZones
        WHERE focusZoneId = :focusZoneId
    """)
    fun observeFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithAppsEntity?>

    @Transaction
    @Query("SELECT * FROM FocusZones")
    fun observeFocusZonesWithApps(): Flow<List<FocusZoneWithAppsEntity>>
}