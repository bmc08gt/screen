package dev.bmcreations.screen.core.extensions

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import com.google.android.material.floatingactionbutton.FloatingActionButton

@SuppressLint("ObjectAnimatorBinding")
fun FloatingActionButton.animateColorChange(@ColorInt fromColor: Int, @ColorInt toColor: Int, startDelay: Long = 0) {
    val colorAnimator = ObjectAnimator.ofArgb(
        this,
        "backgroundTintColor",
        fromColor, toColor
    ).apply {
        setStartDelay(startDelay)
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            backgroundTintList = ColorStateList.valueOf(animatedValue)
        }
    }

    colorAnimator.start()
}
