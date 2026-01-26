package edu.ucne.keepfocus.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.keepfocus.data.local.entities.AppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Upsert
    suspend fun upsertApp(app: AppEntity)

    @Query("""
        SELECT * FROM Apps
        WHERE appId = :appId
        LIMIT 1
    """)
    fun getAppById(appId: Int): Flow<AppEntity>?

    @Delete
    suspend fun deleteApp(app: AppEntity)

    @Query("SELECT * FROM Apps")
    fun getApps(): Flow<List<AppEntity>>
}