package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.FocusZone
import kotlinx.coroutines.flow.Flow

interface FocusZoneRepository {
    suspend fun upsertFocusZone(focusZone: FocusZone)
    suspend fun getFocusZoneById(focusZoneId: Int): Flow<FocusZone>?
    suspend fun deleteFocusZone(focusZone: FocusZone)
    suspend fun getFocusZones(): Flow<List<FocusZone>>
}