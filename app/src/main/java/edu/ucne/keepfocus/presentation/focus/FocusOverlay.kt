package edu.ucne.keepfocus.presentation.focus

sealed interface FocusOverlay {
    data object None: FocusOverlay
    data object AppPicker: FocusOverlay
    data object HelpFocusName: FocusOverlay
    data object HelpTimeLimit: FocusOverlay
    data object IconPicker: FocusOverlay
}