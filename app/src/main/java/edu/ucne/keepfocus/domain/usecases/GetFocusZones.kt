package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.repositories.FocusZoneRepository
import javax.inject.Inject

class GetFocusZones @Inject constructor(
    private val repository: FocusZoneRepository
) {
    suspend operator fun invoke(){
        repository.getFocusZones()
    }
}