package dev.bmcreations.screen.library.view

import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.bmcreations.screen.core.extensions.animateColorChange
import dev.bmcreations.screen.core.extensions.colors
import dev.bmcreations.screen.library.R

internal fun FloatingActionButton.reflectState(recording: Boolean) {
    setImageResource(if (recording) R.drawable.ic_stop else R.drawable.ic_camcorder)
    animateColorChange(
        context.colors[if (recording) R.color.color_secondary else R.color.color_error],
        context.colors[if (recording) R.color.color_error else R.color.color_secondary]
    )
}
