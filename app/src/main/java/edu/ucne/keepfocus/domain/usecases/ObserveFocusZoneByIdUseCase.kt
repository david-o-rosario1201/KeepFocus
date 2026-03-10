package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import javax.inject.Inject

class ObserveFocusZoneByIdUseCase @Inject constructor(
    private val repository: FocusZoneRepository
) {
    operator fun invoke(focusZoneId: Int) = repository.observeFocusZoneById(focusZoneId)
}