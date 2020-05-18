package dev.bmcreations.screen.library.repository

import dev.bmcreations.screen.library.data.FileWatcher
import dev.bmcreations.screen.library.data.watch
import java.io.File

class LibraryRepositoryImpl(
    private val directory: File?
) : LibraryRepository {

    private var watcher : FileWatcher? = null

    override fun getRecordedFiles(callback: (List<File>) -> Unit) {
        watcher = directory?.watch { callback(it) }
    }

    override fun closeWatcher() {
        watcher?.close()
    }
}
