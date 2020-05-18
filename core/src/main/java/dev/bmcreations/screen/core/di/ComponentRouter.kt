package dev.bmcreations.screen.core.di

import android.app.Application

interface Component

object ComponentRouter {

    val components: MutableMap<String, Component> = mutableMapOf()

    fun init(app: Application, block: Initializer.() -> Unit) {
        Initializer().apply {
            this.inject(Components.CORE, CoreComponentImpl(app))
            block.invoke(this)
        }
    }

    class Initializer {
        fun inject(tag: String, component: Component) {
            components[tag] = component
        }
    }

    internal fun component(tag: String): Component? = components[tag]
}

fun <T> String.component() = ComponentRouter.component(this) as T


object Components {
    const val CORE = "core"
    const val LIBRARY = "library"
}
