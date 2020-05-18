package dev.bmcreations.screen.library.view

import dev.bmcreations.screen.core.architecture.BaseViewModel
import dev.bmcreations.screen.library.usecases.CloseFileWatcherUseCase
import dev.bmcreations.screen.library.usecases.WatchFilesUseCase

class LibraryViewModel constructor(
    private val watchUseCase: WatchFilesUseCase,
    private val closeUseCase: CloseFileWatcherUseCase
) : BaseViewModel<LibraryViewState, LibraryViewEvent, LibraryViewEffect>(
    initialState = LibraryViewState()
) {
    override fun process(event: LibraryViewEvent) {
        when (event) {
            LibraryViewEvent.LoadFiles -> loadFiles()
            LibraryViewEvent.CloseWatcher -> closeUseCase.invoke()
        }
    }

    private fun loadFiles() {
        watchUseCase.invoke {
            setState {
                copy(files = it)
            }
        }
    }

    companion object {
        fun create(
            watchUseCase: WatchFilesUseCase,
            closeUseCase: CloseFileWatcherUseCase
        ): LibraryViewModel {
            return LibraryViewModel(watchUseCase, closeUseCase)
        }
    }
}
