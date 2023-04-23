package cz.damat.thebeercounter.scene.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cz.damat.thebeercounter.common.utils.Previews
import cz.damat.thebeercounter.common.utils.collectCommand
import cz.damat.thebeercounter.common.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.common.utils.getOnEvent
import cz.damat.thebeercounter.room.model.Product
import org.koin.androidx.compose.get


/**
 * Created by MD on 23.04.23.
 */
@Composable
fun CounterScreen() {
    val viewModel: CounterScreenViewModel = get()
    val viewState = viewModel.collectStateWithLifecycle()
    val onEvent = viewModel.getOnEvent()
    CommandCollector(viewModel = viewModel)

    CounterScreenContent(viewState = viewState.value, onEvent = onEvent)
}

@Previews
@Composable
private fun Preview() {
    CounterScreenContent(viewState = CounterViewState(), onEvent = {})
}

@Composable
private fun CommandCollector(viewModel: CounterScreenViewModel) {
    val context = LocalContext.current

    viewModel.collectCommand() {
        when (it) {
            else -> {
                //todo
            }
        }
    }
}


@Composable
private fun CounterScreenContent(
    viewState: CounterViewState,
    onEvent: OnEvent
) {
    if (viewState.products == null) {
        Text(text = "TODO")
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            viewState.products.forEach {
                CounterItem(product = it)
            }
        }
    }
}

@Composable
private fun CounterItem(product: Product) {
    //todo
    Text(text = "${product.name} (${product.price?.toPlainString()}) - ${product.count}x")
}