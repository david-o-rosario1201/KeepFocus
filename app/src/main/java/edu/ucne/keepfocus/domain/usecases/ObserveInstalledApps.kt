package edu.ucne.keepfocus.domain.usecases

import edu.ucne.keepfocus.domain.repositories.AppRepository
import javax.inject.Inject

class ObserveInstalledApps @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(){
        repository.observeApps()
    }
}