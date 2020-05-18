package dev.bmcreations.screen.library.view


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dev.bmcreations.scrcast.ScrCast
import dev.bmcreations.screen.core.extensions.animateColorChange
import dev.bmcreations.screen.core.extensions.colors
import dev.bmcreations.screen.core.extensions.hide
import dev.bmcreations.screen.core.extensions.show
import dev.bmcreations.screen.library.R
import dev.bmcreations.screen.library.repository.LibraryRepositoryImpl
import dev.bmcreations.screen.library.usecases.CloseFileWatcherUseCase
import dev.bmcreations.screen.library.usecases.WatchFilesUseCase
import kotlinx.android.synthetic.main.fragment_library.*


class LibraryFragment : Fragment() {

    private val repository by lazy { LibraryRepositoryImpl(recorder?.outputDirectory) }
    private val viewModelFactory: LibraryViewModelFactory by lazy {
        LibraryViewModelFactory(
            watchUseCase = WatchFilesUseCase(repository),
            closeUseCase = CloseFileWatcherUseCase(repository)
        )
    }

    private val viewModel by viewModels<LibraryViewModel> { viewModelFactory }

    private val recorder: ScrCast? by lazy {
        activity?.let {
            ScrCast.use(it).apply {
                updateOptions {
                    copy(directoryName = "screen")
                }
                setOnStateChangeListener { recording ->
                    fab.reflectState(recording)
                }
            }
        }
    }

    private val permissionListener by lazy {
        object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                loadFiles()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        }
    }

    private val dialogPermissionListener: DialogOnAnyDeniedMultiplePermissionsListener by lazy {
        DialogOnAnyDeniedMultiplePermissionsListener.Builder
            .withContext(context)
            .withTitle("Storage permissions")
            .withMessage("Storage permissions are needed to store the screen recording")
            .withButtonText(android.R.string.ok)
            .withIcon(R.drawable.ic_storage_permission_dialog)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(true)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_library, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            val appBarConfiguration = AppBarConfiguration(findNavController().graph)
            setupWithNavController(findNavController(), appBarConfiguration)
        }

        enable_perms.setOnClickListener { requestPermissions() }

        fab.setOnClickListener {
            if (recorder?.isRecording == true) {
                recorder?.stopRecording()
            } else {
                recorder?.record()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (recorder?.hasStoragePermissions() == true) {
            loadFiles()
        } else {
            displayPermissionRationale()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (recorder?.hasStoragePermissions() == true) {
            viewModel.process(LibraryViewEvent.CloseWatcher)
        }
    }

    private fun loadFiles() {
        viewModel.process(LibraryViewEvent.LoadFiles)
        bottom_bar.show(includeFab = fab, onStart = {
            enable_perms.hide()
        })
    }


    private fun displayPermissionRationale() {
        enable_perms.show()
        bottom_bar.hide(fab)
    }

    private fun requestPermissions() {
        Dexter.withContext(context)
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(CompositeMultiplePermissionsListener(permissionListener, dialogPermissionListener))
            .check()
    }

    private fun FloatingActionButton.reflectState(recording: Boolean) {
        setImageResource(if (recording) R.drawable.ic_stop else R.drawable.ic_camcorder)
        animateColorChange(
            context.colors[if (recording) R.color.color_secondary else R.color.color_error],
            context.colors[if (recording) R.color.color_error else R.color.color_secondary]
        )
    }
}