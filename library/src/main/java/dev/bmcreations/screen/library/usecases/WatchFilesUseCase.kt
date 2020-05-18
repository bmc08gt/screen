package dev.bmcreations.screen.library.usecases

import dev.bmcreations.screen.core.extensions.size
import dev.bmcreations.screen.core.extensions.sizeStrWithMb
import dev.bmcreations.screen.library.models.LibraryFile
import dev.bmcreations.screen.library.repository.LibraryRepository

class WatchFilesUseCase(
    private val repository: LibraryRepository
) {
    operator fun invoke(callback: (List<LibraryFile>) -> Unit) {
        repository.getRecordedFiles {
            callback(it.mapNotNull { file ->
                if (file.size > 0) {
                    LibraryFile(
                        name = file.nameWithoutExtension,
                        path = file.absolutePath,
                        size = file.sizeStrWithMb(2)
                    )
                } else {
                    null
                }
            })
        }
    }
}
