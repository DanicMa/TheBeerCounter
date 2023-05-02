package cz.damat.thebeercounter.scene.counter

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.common.utils.Previews
import cz.damat.thebeercounter.common.utils.collectCommand
import cz.damat.thebeercounter.common.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.common.utils.getOnEvent
import cz.damat.thebeercounter.room.model.Product
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

    viewModel.collectCommand {
        when (it) {
            is CounterCommand.ShowSetCountDialog -> {
                showSetCountDialogForProduct.value = it.product
            }
            CounterCommand.ShowClearAllConfirmDialog -> showClearAllConfirmDialog.value = true
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
            Text(text = "TODO")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    stickyHeader {
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
                        }
                    }

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
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SetCountDialog(productState: MutableState<Product?>, onEvent: OnEvent) {
    val product = productState.value ?: return

    val textFieldValue = remember {
        val text = product.count.toString()
        mutableStateOf(TextFieldValue(text = text, selection = TextRange(0, text.length)))
    }

    val focusRequester = remember { FocusRequester() }

    val onConfirmClick: () -> Unit = {
        productState.value = null
        textFieldValue.value.text.toIntOrNull()?.let {
            onEvent(CounterEvent.OnCountSet(product.id, it))
        }
    }

    DialogThemed(
        showDialog = null,
        title = stringResource(id = R.string.action_set_count),
        text = null,
        confirmString = stringResource(id = R.string.action_set),
        dismissString = stringResource(id = R.string.cancel),
        onConfirmClick = onConfirmClick,
        onDismissClick = {
            productState.value = null
        },
        content = {
            val keyboardController = LocalSoftwareKeyboardController.current

            //todo prettier layout - some padding?
            TextField(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        onConfirmClick()
                    }
                ),
                value = textFieldValue.value,
                onValueChange = { tfv ->
                    textFieldValue.value = tfv
                }
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    )
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