package edu.ucne.keepfocus.presentation.focus

sealed interface FocusUiEvent {
    data object OnOpenModal: FocusUiEvent
    data object OnCloseModal: FocusUiEvent
    data class OnSelectedApp(val packageName: String): FocusUiEvent
    data class OnAddSelectedApps(val apps: List<AppUi>): FocusUiEvent
    data class OnDeleteSelectedApp(val packageName: String): FocusUiEvent
}