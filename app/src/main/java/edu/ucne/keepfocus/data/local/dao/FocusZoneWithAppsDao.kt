package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.ucne.keepfocus.data.local.entities.FocusZoneWithApps
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusZoneWithAppsDao {
    @Transaction
    @Query("""
        SELECT * FROM FocusZones
        WHERE focusZoneId = :focusZoneId
    """)
    suspend fun getFocusZoneWithApps(focusZoneId: Int): FocusZoneWithApps

    @Transaction
    @Query("SELECT * FROM FocusZones")
    suspend fun getFocusZoneWithApps(): Flow<List<FocusZoneWithApps>>
}