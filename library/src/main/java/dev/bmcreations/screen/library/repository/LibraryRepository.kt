package dev.bmcreations.screen.library.repository

import java.io.File

interface LibraryRepository {
    fun getRecordedFiles(callback: (List<File>) -> Unit)
    fun closeWatcher()
}
