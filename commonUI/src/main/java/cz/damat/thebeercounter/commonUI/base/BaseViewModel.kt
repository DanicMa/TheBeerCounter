package cz.damat.thebeercounter.commonUI.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.damat.thebeercounter.commonUI.utils.baseCoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by MD on 29.12.22.
 */
abstract class BaseViewModel<STATE : ViewStateDTO, EVENT : ViewEvent, COMMAND : ViewCommand>(val initialState: STATE) : ViewModel() {

    // todo - use explicit backing fields when project is switched to Kotlin 2.0, see https://github.com/Kotlin/KEEP/blob/explicit-backing-fields-re/proposals/explicit-backing-fields.md

    // For current state that should be represented by the view
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: Flow<STATE> = _stateFlow.asStateFlow()

    // For handling events from UI to VM
    val eventChannel = Channel<EVENT>(Channel.UNLIMITED)

    // For handling commands from VM to UI
    private val commandChannel = Channel<COMMAND>()
    val commandFlow: Flow<COMMAND> = commandChannel.receiveAsFlow()

    protected val viewModelScopeExHandled = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + baseCoroutineExceptionHandler)

    init {
        eventChannel.consumeAsFlow().onEach {
            onEvent(it)
        }.launchIn(viewModelScopeExHandled)
    }

    protected fun BaseViewModel<STATE, EVENT, COMMAND>.launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScopeExHandled.launch(context, start, block)
    }

    /**
     * Gets the current view state.
     */
    protected fun currentState(): STATE {
        return _stateFlow.value
    }

    /**
     * Updates view state with a new value.
     */
    protected fun updateState(body: STATE.() -> STATE) {
        _stateFlow.value = body(_stateFlow.value)
    }

    /**
     * An event was received from the UI and the ViewModel should handle it.
     */
    protected abstract fun onEvent(event: EVENT)


    /**
     * Post a command to the UI.
     */
    protected fun sendCommand(command: COMMAND) {
        launch {
            commandChannel.send(command)
        }
    }

    /**
     * Cancel our custom-handled coroutine when clearing the viewModel
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScopeExHandled.cancel()
    }
}