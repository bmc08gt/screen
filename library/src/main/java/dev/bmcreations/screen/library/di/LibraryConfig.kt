package dev.bmcreations.screen.library.di

import dev.bmcreations.screen.core.di.Component
import dev.bmcreations.screen.core.di.CoreComponent

interface LibraryComponent: Component {
    val core: CoreComponent
}

class LibraryComponentImpl(
    override val core: CoreComponent
): LibraryComponent
