package dev.bmcreations.screen.core.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : ViewState, E : ViewStateEvent, X : ViewStateEffect>(private val initialState: S) :
    ViewModel(), ViewModelContract<E>, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    val state: MutableLiveData<S> = MutableLiveData()

    protected fun getLastState(): S? = state.value ?: initialState

    protected val effectsEmitter = EventEmitter<X>()
    val effects: EventSource<X> get() = effectsEmitter

    init {
        setState { initialState }
    }

    fun setState(stateModifier: S.() -> S) {
        viewModelScope.launch {
            state.value = stateModifier.invoke(state.value ?: initialState)
        }
    }
}

/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
internal interface ViewModelContract<E : ViewStateEvent> {
    fun process(event: E)
}
