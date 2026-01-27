package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFocusZonesWithApps @Inject constructor(
    private val repository: FocusZoneRepository
) {
    operator fun invoke(): Flow<List<FocusZoneWithApps>> = repository.observeFocusZonesWithApps()
}