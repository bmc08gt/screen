package dev.bmcreations.screen.library.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.bmcreations.screen.library.usecases.CloseFileWatcherUseCase
import dev.bmcreations.screen.library.usecases.WatchFilesUseCase

class LibraryViewModelFactory constructor(
    val watchUseCase: WatchFilesUseCase,
    val closeUseCase: CloseFileWatcherUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LibraryViewModel.create(watchUseCase, closeUseCase) as T
    }


}
