package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import edu.ucne.keepfocus.domain.repositories.FocusZoneWithAppsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFocusZonesWithApps @Inject constructor(
    private val repository: FocusZoneWithAppsRepository
) {
    operator fun invoke(): Flow<List<FocusZoneWithApps>> = repository.getFocusZoneWithApps()
}