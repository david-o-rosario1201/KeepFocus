package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import javax.inject.Inject

class ObserveFocusZoneWithAppsUseCase @Inject constructor(
    private val repository: FocusZoneRepository
) {
    operator fun invoke(focusZoneId: Int) = repository.observeFocusZoneWithApps(focusZoneId)
}