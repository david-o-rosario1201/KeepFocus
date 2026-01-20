package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.ucne.keepfocus.data.local.entities.FocusZoneWithAppsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusZoneWithAppsDao {
    @Transaction
    @Query("""
        SELECT * FROM FocusZones
        WHERE focusZoneId = :focusZoneId
    """)
    suspend fun getFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithAppsEntity>?

    @Transaction
    @Query("SELECT * FROM FocusZones")
    suspend fun getFocusZoneWithApps(): Flow<List<FocusZoneWithAppsEntity>>
}