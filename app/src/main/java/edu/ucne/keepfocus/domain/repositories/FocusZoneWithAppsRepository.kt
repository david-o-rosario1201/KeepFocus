package edu.ucne.keepfocus.domain.repositories

import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import kotlinx.coroutines.flow.Flow

interface FocusZoneWithAppsRepository {
    fun getFocusZoneWithApps(focusZoneId: Int): Flow<FocusZoneWithApps>?
    fun getFocusZoneWithApps(): Flow<List<FocusZoneWithApps>>
}