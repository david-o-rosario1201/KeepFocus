package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.repositories.FocusZoneWithAppsRepository
import javax.inject.Inject

class GetFocusZoneWithApps @Inject constructor(
    private val repository: FocusZoneWithAppsRepository
) {
    suspend operator fun invoke(){
        repository.getFocusZoneWithApps()
    }
}