package cz.damat.thebeercounter.scene.counter

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.common.utils.Previews
import cz.damat.thebeercounter.common.utils.collectCommand
import cz.damat.thebeercounter.common.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.common.utils.getOnEvent
import cz.damat.thebeercounter.room.entity.Product
import cz.damat.thebeercounter.scene.counter.dialog.AddNewProductDialog
import cz.damat.thebeercounter.scene.counter.dialog.SetCountDialog
import cz.damat.thebeercounter.ui.component.*
import cz.damat.thebeercounter.ui.theme.disabled
import org.koin.androidx.compose.get
import java.text.NumberFormat
import java.util.*


/**
 * Created by MD on 23.04.23.
 */
@Composable
fun CounterScreen() {
    val viewModel: CounterScreenViewModel = get()
    val viewState = viewModel.collectStateWithLifecycle()
    val onEvent = viewModel.getOnEvent()
    CommandCollector(viewModel = viewModel, onEvent)

    CounterScreenContent(viewState = viewState.value, onEvent = onEvent)
}

@Previews
@Composable
private fun Preview() {
    CounterScreenContent(viewState = CounterViewState(), onEvent = {})
}

@Composable
private fun CommandCollector(
    viewModel: CounterScreenViewModel,
    onEvent: OnEvent
) {
    val showSetCountDialogForProduct = remember {
        mutableStateOf<Product?>(null)
    }

    val showClearAllConfirmDialog = remember {
        mutableStateOf(false)
    }

    val showAddNewDialog = remember {
        mutableStateOf(false)
    }

    viewModel.collectCommand {
        when (it) {
            is CounterCommand.ShowSetCountDialog -> {
                showSetCountDialogForProduct.value = it.product
            }
            CounterCommand.ShowClearAllConfirmDialog -> showClearAllConfirmDialog.value = true
            CounterCommand.ShowAddNewDialog -> showAddNewDialog.value = true
        }
    }

    SetCountDialog(
        productState = showSetCountDialogForProduct,
        onEvent = onEvent
    )

    ClearAllConfirmDialog(
        showDialog = showClearAllConfirmDialog,
        onEvent = onEvent
    )

    AddNewProductDialog(
        showDialog = showAddNewDialog,
        onNewProductCreated = {
            onEvent(CounterEvent.OnNewProductAdded(it))
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CounterScreenContent(
    viewState: CounterViewState,
    onEvent: OnEvent
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (viewState.products == null) {
            //todo loading shimmer
            Text(text = "Loading...")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    stickyHeader {
                        Header(onEvent)
                    }

                    items(viewState.products, key = { it.id }) {
                        CounterItem(product = it, onEvent = onEvent)
                    }
                })
        }
    }
}

@Composable
private fun Header(onEvent: OnEvent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.your_tab),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = { onEvent(CounterEvent.OnClearAllClicked) }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = stringResource(id = R.string.action_clear_all)
            )
        }

        IconButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = { onEvent(CounterEvent.OnAddNewClicked) }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = stringResource(id = R.string.action_add)
            )
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
                    imageVector = Icons.Default.MoreVert,
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
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.h5
                    )

                    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
                        //todo - user-changable currency
                        currency = Currency.getInstance("CZK")
                        minimumFractionDigits = 0
                    }

                    val priceString: String = product.price?.let {
                        format.format(0f)
                    } ?: ""

                    // todo - nicer layout when price is not set?
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
    //todo - modify product item and dialog
}

@Composable
fun ClearAllConfirmDialog(showDialog: MutableState<Boolean>, onEvent: OnEvent) {
    ConfirmDialog(
        showDialog = showDialog,
        title = stringResource(id = R.string.action_clear_all),
        text = stringResource(id = R.string.clear_all_confirm_message),
        confirmString = stringResource(id = R.string.clear),
        onConfirmClick = { onEvent(CounterEvent.OnClearAllConfirmed) })
}