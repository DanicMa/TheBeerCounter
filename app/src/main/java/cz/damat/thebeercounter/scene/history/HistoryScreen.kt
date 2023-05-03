package cz.damat.thebeercounter.scene.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.common.utils.*
import cz.damat.thebeercounter.room.dto.HistoryProduct
import cz.damat.thebeercounter.ui.component.CardThemed
import cz.damat.thebeercounter.ui.theme.DarkGreen
import cz.damat.thebeercounter.ui.theme.disabled
import org.koin.androidx.compose.get
import java.text.NumberFormat
import java.util.*


/**
 * Created by MD on 03.05.23.
 */
@Composable
fun HistoryScreen() {
    val viewModel: HistoryViewModel = get()
    val viewState = viewModel.collectStateWithLifecycle()
    val onEvent = viewModel.getOnEvent()

    CommandCollector(viewModel = viewModel, onEvent)

    HistoryScreenContent(viewState = viewState.value, onEvent = onEvent)
}

@Previews
@Composable
private fun Preview() {
    HistoryScreenContent(viewState = HistoryViewState(), onEvent = {})
}


@Composable
private fun CommandCollector(
    viewModel: HistoryViewModel,
    onEvent: OnEvent
) {
    viewModel.collectCommand(block = { command ->
        when (command) {
            else -> {}
        }
    })
}

@Composable
private fun HistoryScreenContent(
    viewState: HistoryViewState,
    onEvent: OnEvent
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (viewState.items == null) {
            // todo - loading shimmer
            Text("Loading...")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = viewState.items, key = { it.historyItem.id }) { historyProduct ->
                    HistoryProductItem(item = historyProduct, onEvent = onEvent)
                }
            }
        }
    }
}

@Composable
private fun HistoryProductItem(
    item: HistoryProduct,
    onEvent: OnEvent
) {
    val dropdownShown = remember {
        mutableStateOf(false)
    }

    val countChange = item.historyItem.newCount - item.historyItem.oldCount

    CardThemed(
        modifier = Modifier
            .fillMaxWidth(),
//        borderColor = Color.White,
        onClick = null
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Column {
                    Text(
                        text = item.product.name,
                        style = MaterialTheme.typography.h5
                    )

                    val timeString: String = item.historyItem.date.formatForTimeOrDayAndTime(LocalContext.current)
                    Text(
                        text = timeString,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface.disabled
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max)
            ) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = countChange.toStringSigned(),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.h5,
                        color = if (countChange > 0) DarkGreen else Color.Red
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${item.historyItem.oldCount} -> ${item.historyItem.newCount}",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface.disabled
                    )
                }
            }
        }
    }
}