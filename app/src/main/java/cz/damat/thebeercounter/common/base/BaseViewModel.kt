package cz.damat.thebeercounter.common.base

import androidx.lifecycle.ViewModel
import cz.damat.thebeercounter.common.utils.baseCoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * Created by MD on 29.12.22.
 */
abstract class BaseViewModel<STATE : ViewState, EVENT : ViewEvent, COMMAND : ViewCommand>(val initialState: STATE) : ViewModel() {

    // todo - use explicit backing fields when project is switched to Kotlin 2.0, see https://github.com/Kotlin/KEEP/blob/explicit-backing-fields-re/proposals/explicit-backing-fields.md

    // For current state that should be represented by the view
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: Flow<STATE> = _stateFlow.asStateFlow()

    // For handling events from UI to VM
    val eventChannel = Channel<EVENT>(Channel.UNLIMITED)

    // For handling commands from VM to UI
    private val _commandFlow = MutableSharedFlow<COMMAND>()
    val commandFlow: Flow<COMMAND> = _commandFlow.asSharedFlow()

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelSupervisorJob = SupervisorJob()

    /**
     * For running coroutines on the main thread. Use only for UI related work.
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelSupervisorJob + baseCoroutineExceptionHandler)

    /**
     * For performing background work like DB access/network calls/...
     * Use only for non-UI related work.
     */
    protected val ioScope = CoroutineScope(Dispatchers.IO + viewModelSupervisorJob + baseCoroutineExceptionHandler)

    /**
     * Optimized for CPU intensive work like sorting/filtering/parsing...
     */
    protected val defaultScope = CoroutineScope(Dispatchers.Default + viewModelSupervisorJob + baseCoroutineExceptionHandler)

    init {
        defaultScope.launch {
            eventChannel.consumeAsFlow().collect {
                onEvent(it)
            }
        }
    }

    /**
     * Updates view state with a new value.
     */
    protected fun updateState(body: STATE.() -> STATE) {
        synchronized(_stateFlow) {
            _stateFlow.value = body(_stateFlow.value)
        }
    }

    /**
     * An event was received from the UI and the ViewModel should handle it.
     */
    protected abstract fun onEvent(event: EVENT)


    /**
     * Post a command to the UI.
     */
    protected suspend fun sendCommand(command : COMMAND) {
        _commandFlow.emit(command)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelSupervisorJob.cancel() //cnaceling the supervisor cancels all child scopes
    }
}