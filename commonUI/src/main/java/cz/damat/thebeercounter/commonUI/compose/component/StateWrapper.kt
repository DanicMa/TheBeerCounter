package cz.damat.thebeercounter.commonUI.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.commonUI.base.State


/**
 * Created by MD on 08.11.23.
 */
@Composable
fun StateWrapper(
    modifier: Modifier = Modifier,
    state: State,
    customLoading: @Composable (BoxScope.() -> Unit)? = null,
    customError: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        when (state) {
            State.Content -> content()
            is State.Error -> customError?.invoke(this) ?: Error(message = stringResource(id = state.message))
            State.Loading -> customLoading?.invoke(this) ?: Loading()
        }
    }
}

@Composable
private fun BoxScope.Loading(
) {
    CircularProgressIndicator(
        modifier = Modifier
            .align(Alignment.Center)
            .size(64.dp),
    )
}

@Composable
private fun BoxScope.Error(
    message: String,
) {
    Text(
        modifier = Modifier.align(Alignment.Center),
        text = message
    )
}