package dev.bmcreations.screen

import android.app.Application
import dev.bmcreations.screen.core.di.ComponentRouter
import dev.bmcreations.screen.core.di.Components
import dev.bmcreations.screen.core.di.component
import dev.bmcreations.screen.library.di.LibraryComponentImpl

class Screen : Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentRouter.init(this) {
            inject(
                Components.LIBRARY,
                LibraryComponentImpl(Components.CORE.component())
            )
        }
    }
}
