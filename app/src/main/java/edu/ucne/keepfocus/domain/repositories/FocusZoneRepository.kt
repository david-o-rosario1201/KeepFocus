package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import kotlinx.coroutines.flow.Flow

interface FocusZoneRepository {
    suspend fun upsertFocusZone(focusZone: FocusZone)
    fun observeFocusZoneById(focusZoneId: Int): Flow<FocusZone?>
    suspend fun deleteFocusZone(focusZone: FocusZone)
    fun observeFocusZones(): Flow<List<FocusZone>>
    fun observeFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithApps?>
    fun observeFocusZonesWithApps(): Flow<List<FocusZoneWithApps>>
}