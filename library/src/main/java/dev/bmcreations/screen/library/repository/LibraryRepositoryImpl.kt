package dev.bmcreations.screen.library.repository

import dev.bmcreations.screen.library.data.FileWatcher
import dev.bmcreations.screen.library.data.watch
import java.io.File

class LibraryRepositoryImpl(
    private val directory: File?
) : LibraryRepository {

    private var watcher : FileWatcher? = null

    private var watching = false

    override fun getRecordedFiles(callback: (List<File>) -> Unit) {
        watching = true
        watcher = directory?.watch {
            if (watching) {
                callback(it)
            }
        }
    }

    override fun closeWatcher() {
        watching = false
        watcher?.close()
    }
}
