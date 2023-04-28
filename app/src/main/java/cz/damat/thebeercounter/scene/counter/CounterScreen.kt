package cz.damat.thebeercounter.scene.counter

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.common.utils.Previews
import cz.damat.thebeercounter.common.utils.collectCommand
import cz.damat.thebeercounter.common.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.common.utils.getOnEvent
import cz.damat.thebeercounter.room.model.Product
import cz.damat.thebeercounter.ui.component.CardThemed
import cz.damat.thebeercounter.ui.component.DropdownItem
import cz.damat.thebeercounter.ui.component.DropdownMenu
import cz.damat.thebeercounter.ui.theme.disabled
import org.koin.androidx.compose.get
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


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
    Column(modifier = Modifier.padding(16.dp)) {
        if (viewState.products == null) {
            Text(text = "TODO")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(viewState.products, key = { it.id }) {
                        CounterItem(product = it, onEvent = onEvent)
                    }
                })
        }
    }
}

@Composable
private fun CounterItem(
    product: Product,
    onEvent: OnEvent
) {
    val dropdownShown = remember {
        mutableStateOf(false)
    }

    CardThemed(
        modifier = Modifier
            .fillMaxWidth(),
//        .height(92.dp),
//        borderColor = MaterialTheme.colors.primary,
        borderColor = Color.White,
        onClick = {
            onEvent(CounterEvent.OnProductClicked(product.id))
        }) {

        val horizontalPadding = 24.dp

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .padding(start = 4.dp, end = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { dropdownShown.value = true }) {
                Icon(
                    modifier = Modifier.rotate(90f),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_horizontal_24),
                    contentDescription = stringResource(id = R.string.more)
                )
            }
            ProductDropdown(shown = dropdownShown, product = product, onEvent = onEvent)

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Column(
//                    modifier = Modifier.padding(start = horizontalPadding)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.h5
                    )

                    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
                        currency = Currency.getInstance("CZK")
                        minimumFractionDigits = 0
                    }

                    val priceString: String = product.price?.let {
//                        format.format(product.price)
                        format.format(0f)
                    } ?: ""

                    // todo - nice layout when price is not set?
                    Text(
                        text = priceString,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface.disabled
                    )
                }

            }

            Text(
                modifier = Modifier,
                text = product.count.toString(),
                style = MaterialTheme.typography.h3
            )
        }
    }

}

@Composable
private fun ProductDropdown(shown: MutableState<Boolean>, product: Product, onEvent: OnEvent) {
    val dropdownItems = MenuItem.values().map {
        DropdownItem(
            text = stringResource(id = it.titleRes),
            onClick = {
                onEvent(CounterEvent.OnMenuItemClicked(it, product.id))
                shown.value = false
            }
        )
    }
    DropdownMenu(dropdownShown = shown, scaffoldState = null, items = dropdownItems)
}

enum class MenuItem(@StringRes val titleRes: Int) {
    Reset(R.string.action_reset),
    Hide(R.string.action_delete),
    SetCount(R.string.action_set_count),
}