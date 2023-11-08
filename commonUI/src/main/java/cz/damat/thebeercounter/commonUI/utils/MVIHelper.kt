package cz.damat.thebeercounter.commonUI.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import cz.damat.thebeercounter.commonUI.base.BaseViewModel
import cz.damat.thebeercounter.commonUI.base.ViewCommand
import cz.damat.thebeercounter.commonUI.base.ViewEvent
import cz.damat.thebeercounter.commonUI.base.ViewStateDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


/**
 * Created by MD on 23.04.23.
 */

/**
 * Convenience extension function for [Flow.collectAsStateWithLifecycle] without needing to provide initial viewState values both here and in VM.
 * Collects the [BaseViewModel.stateFlow] by using [collectAsStateWithLifecycle] and the [BaseViewModel.initialState].
 */
@Composable
fun <T : ViewStateDTO> BaseViewModel<T, *, *>.collectStateWithLifecycle(): State<T> {
    return stateFlow.collectAsStateWithLifecycle(initialValue = this.initialState)
}

/**
 * Convenience extension function for obtaining a remembered function type that can be passed further down the composition tree and be used to send events to the viewModel.
 */
@Composable
fun <T : ViewEvent> BaseViewModel<*, T, *>.getOnEvent(): ((T) -> Unit) {
    // channel posting is done on the coroutine scope since it really is a view action and the calling composable coming out of composition should cause cancellation of the coroutine
    val scope = rememberCoroutineScope() + baseCoroutineExceptionHandler

    // we need to use remember otherwise every recomposition (which happens on any change of any parameter since this method is probably not called from skippable methods) would create new instance and cause recomposition of the subsequent composable even if they would be skippable
    return remember {
        { event ->
            scope.launch {
                eventChannel.send(event)
            }
        }
    }
}

/**
 * Convenience extension function for collecting commands from the [BaseViewModel.commandFlow] and passing them to the [block] function.
 * This function uses an unchanging LaunchEffect to start collection of the hot [BaseViewModel.commandFlow] and the [LocalLifecycleOwner] to stop collection when the lifecycle is not in [Lifecycle.State.STARTED] state.
 *
 */
@Composable
fun <T : ViewCommand> BaseViewModel<*, *, T>.collectCommand(
    block: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        // unchanging key makes sure that the collection is started only once

        commandFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(block) // since the command flow in not a StateFlow the block is called only at the time something is posted to the flow.
            .launchIn(this + baseCoroutineExceptionHandler)
    }
}