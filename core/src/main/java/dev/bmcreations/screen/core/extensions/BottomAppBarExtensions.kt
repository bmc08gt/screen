package dev.bmcreations.screen.core.extensions

import androidx.core.view.doOnPreDraw
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun BottomAppBar.show(
    includeFab: FloatingActionButton? = null,
    fabAnimationDelay: Long = 225 + 60, // app bar enter animation duration is 225
    onStart: (() -> Unit)? = null
) {
    with(behavior as HideBottomViewOnScrollBehavior<BottomAppBar>) {
        this.slideUp(this@show)
        onStart?.invoke()
        includeFab?.let { fab ->
                fab.doOnPreDraw {
                    it.postDelayed({ fab.show() }, fabAnimationDelay)
                }
        }
    }
}

fun BottomAppBar.hide(
    includeFab: FloatingActionButton? = null,
    appBarAnimationDelay: Long = 300
) {
    with(behavior as HideBottomViewOnScrollBehavior<BottomAppBar>) {
        includeFab?.let { fab ->
            fab.doOnPreDraw {
                fab.hide()
                postDelayed({ this.slideDown(this@hide) }, appBarAnimationDelay)
            }
        }
    }
}
