package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.FocusZone
import kotlinx.coroutines.flow.Flow

interface FocusZoneRepository {
    suspend fun upsertFocusZone(focusZone: FocusZone)
    fun getFocusZoneById(focusZoneId: Int): Flow<FocusZone>?
    suspend fun deleteFocusZone(focusZone: FocusZone)
    fun getFocusZones(): Flow<List<FocusZone>>
}