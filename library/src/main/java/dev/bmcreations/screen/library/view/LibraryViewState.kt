package dev.bmcreations.screen.library.view

import dev.bmcreations.screen.core.architecture.ViewState
import dev.bmcreations.screen.core.architecture.ViewStateEffect
import dev.bmcreations.screen.core.architecture.ViewStateEvent
import dev.bmcreations.screen.library.models.LibraryFile
import java.io.File

data class LibraryViewState(
    val files: List<LibraryFile> = emptyList()
): ViewState

sealed class LibraryViewEvent : ViewStateEvent() {
    object LoadFiles : LibraryViewEvent()
    object CloseWatcher : LibraryViewEvent()
}
sealed class LibraryViewEffect : ViewStateEffect()
