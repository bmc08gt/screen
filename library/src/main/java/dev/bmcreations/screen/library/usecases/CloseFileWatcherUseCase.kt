package dev.bmcreations.screen.library.usecases

import dev.bmcreations.screen.library.repository.LibraryRepository

class CloseFileWatcherUseCase(
    private val repository: LibraryRepository
) {
    operator fun invoke() = repository.closeWatcher()
}
