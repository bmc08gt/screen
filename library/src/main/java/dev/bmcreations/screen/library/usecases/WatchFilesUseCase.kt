package dev.bmcreations.screen.library.usecases

import dev.bmcreations.screen.library.repository.LibraryRepository
import java.io.File

class WatchFilesUseCase(
    private val repository: LibraryRepository
) {
    operator fun invoke(callback: (List<File>) -> Unit) {
        repository.getRecordedFiles(callback)
    }
}
