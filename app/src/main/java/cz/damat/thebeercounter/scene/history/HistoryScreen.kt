package cz.damat.thebeercounter.scene.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.common.utils.*
import cz.damat.thebeercounter.room.dto.HistoryProduct
import cz.damat.thebeercounter.room.entity.HistoryItemType
import cz.damat.thebeercounter.ui.theme.disabled
import org.koin.androidx.compose.get


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
    // todo - add filter option (e.g. only adding)

    Column {
        if (viewState.items == null) {
            // todo - loading shimmer
            Text("Loading...")
        } else {
            if (viewState.items.isEmpty()) {
                //todo - empty placeholder
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    itemsIndexed(items = viewState.items, key = { index, item -> item.day }) { index, item ->
                        HistoryProductDay(item = item, onEvent = onEvent)

                        if (index < viewState.items.lastIndex) {
                            // divider for all but last item
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryProductDay(
    item: DayToHistoryDTO,
    onEvent: OnEvent
) {
    val horizontalPadding = 16.dp

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(HistoryEvent.OnHistoryItemClick(item))
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = horizontalPadding
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = item.day.formatToDayString(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                )

                Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                    IconButton(onClick = { onEvent(HistoryEvent.OnHistoryItemClick(item)) }) {
                        Icon(
                            modifier = Modifier
                                .graphicsLayer {
                                    if (item.isExpanded) rotationZ = 180f
                                },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_expand_more_24),
                            contentDescription = stringResource(id = if (item.isExpanded) R.string.collapse else R.string.expand),
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = item.isExpanded,
        ) {
            Column(
                modifier = Modifier.padding(
                    PaddingValues(
                        start = horizontalPadding * 3,
                        top = 0.dp,
                        end = horizontalPadding,
                        bottom = 8.dp
                    )
                )
            ) {
                item.historyItems.forEach { historyItem ->
                    HistoryProductItem(item = historyItem, onEvent = onEvent)
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
    //todo - dropdown for deleting OR swipe to delete?

    val countChange = item.historyItem.newCount - item.historyItem.oldCount

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

                val timeString: String = item.historyItem.date.formatToTimeString(LocalContext.current)
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
                    color = if (countChange < 0) Color.Red else MaterialTheme.colors.onSurface
                )

                if (item.historyItem.type != HistoryItemType.ADD) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${item.historyItem.oldCount} ➔ ${item.historyItem.newCount}",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface.disabled
                    )
                }
            }
        }
    }
}