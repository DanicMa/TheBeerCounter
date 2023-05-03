package cz.damat.thebeercounter.scene.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.damat.thebeercounter.common.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.common.utils.getOnEvent
import org.koin.androidx.compose.get


/**
 * Created by MD on 03.05.23.
 */
@Composable
fun HistoryScreen() {
    val viewModel: HistoryViewModel = get()
    val viewState = viewModel.collectStateWithLifecycle()
    val onEvent = viewModel.getOnEvent()

    if (viewState.value.items == null) {
        // todo - loading shimmer
        Text("Loading...")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = viewState.value.items!!) { historyProduct ->
                Text(historyProduct.Product.name)
            }
        }
    }

//    CommandCollector(viewModel = viewModel, onEvent)
//
//    CounterScreenContent(viewState = viewState.value, onEvent = onEvent)
}