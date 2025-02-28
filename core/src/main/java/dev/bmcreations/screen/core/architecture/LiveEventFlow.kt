package dev.bmcreations.screen.core.architecture

import androidx.lifecycle.*
import com.zhuinden.eventemitter.EventSource

private class LiveEventSource<T> constructor(
    private val eventSource: EventSource<T>,
    private val lifecycleOwner: LifecycleOwner,
    private val observer: EventSource.EventObserver<T>
) : LifecycleObserver {
    init {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            lifecycleOwner.lifecycle.addObserver(this)
        }
    }

    private var isActive: Boolean = false
    private var notificationToken: EventSource.NotificationToken? = null

    private fun shouldBeActive(): Boolean {
        return lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }

    private fun disposeObserver() {
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            stopListening()
            disposeObserver()
            return
        }
        checkIfActiveStateChanged(shouldBeActive())
    }

    private fun checkIfActiveStateChanged(newActive: Boolean) {
        if (newActive == isActive) {
            return
        }
        val wasActive = isActive
        isActive = newActive
        val isActive = isActive

        if (!wasActive && isActive) {
            stopListening()
            notificationToken = eventSource.startListening(observer)
        }

        if (wasActive && !isActive) {
            stopListening()
        }
    }

    private fun stopListening() {
        notificationToken?.stopListening()
        notificationToken = null
    }
}

fun <T> EventSource<T>.observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    LiveEventSource(this, lifecycleOwner, EventSource.EventObserver { event -> observer.onChanged(event) })
}
