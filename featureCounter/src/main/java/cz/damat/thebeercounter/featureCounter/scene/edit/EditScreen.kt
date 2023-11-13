package cz.damat.thebeercounter.featureCounter.scene.edit

import android.widget.Space
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.compose.component.TBCButton
import cz.damat.thebeercounter.commonUI.compose.component.TBCStateWrapper
import cz.damat.thebeercounter.commonUI.compose.component.TBCTextField
import cz.damat.thebeercounter.commonUI.utils.Previews
import cz.damat.thebeercounter.commonUI.utils.collectCommand
import cz.damat.thebeercounter.commonUI.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.commonUI.utils.getOnEvent
import cz.damat.thebeercounter.featureCounter.scene.counter.CounterEvent
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


/**
 * Created by MD on 07.11.23.
 */
@Composable
fun EditScreen(
    navController: NavController,
    productId: Int?,
) {
    val viewModel: EditScreenViewModel = getViewModel() {
        parametersOf(productId)
    }
    val viewState = viewModel.collectStateWithLifecycle()
    val onEvent = viewModel.getOnEvent()

    CommandCollector(viewModel, navController)
    EditScreenContent(viewState = viewState.value, onEvent = onEvent)
}

@Previews
@Composable
private fun Preview() {
    EditScreenContent(viewState = EditViewState(), onEvent = {})
}

@Composable
private fun CommandCollector(
    viewModel: EditScreenViewModel,
    navController: NavController,
) {
    viewModel.collectCommand(block = { command ->
        when (command) {
            EditCommand.NavigateBack -> navController.navigateUp()
        }
    })
}


@Composable
private fun EditScreenContent(
    viewState: EditViewState,
    onEvent: OnEvent
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(text = stringResource(id = R.string.edit_product))
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(EditEvent.OnBackClick) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        TBCStateWrapper(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), state = viewState.state
        ) {
            EditForm(
                productName = viewState.productName,
                productCount = viewState.productCount,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun EditForm(
    productName: String,
    productCount: String,
    onEvent: OnEvent
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .safeContentPadding()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TBCTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.name),
            value = productName,
            hasNextDownImeAction = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            onValueChanged = { name -> onEvent(EditEvent.OnProductNameChange(name)) }
        )

        TBCTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.count),
            value = productCount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            trailingIcon = {
                Row {
                    RemoveAddButton(add = false, productCount = productCount, onEvent = onEvent)
                    RemoveAddButton(add = true, productCount = productCount, onEvent = onEvent)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            },
            onValueChanged = {
                onEvent(EditEvent.OnProductCountChange(it))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        TBCButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.ok),
            enabled = productName.isNotBlank(),
            onClick = { onEvent(EditEvent.OnSaveClick) }
        )
    }
}

@Composable
fun RemoveAddButton(
    add: Boolean,
    productCount: String,
    onEvent: OnEvent,
) {
    IconButton(
        modifier = Modifier.fillMaxHeight(),
        onClick = {
            productCount.toIntOrNull()?.let {
                onEvent(EditEvent.OnProductCountChange((if (add) it + 1 else it - 1).toString()))
            }
        }
    ) {
        val imageVector = if (add) {
            Icons.Default.Add
        } else {
            ImageVector.vectorResource(id = R.drawable.ic_remove_24)
        }

        val contentDescription = stringResource(
            id = if (add) {
                R.string.action_add
            } else {
                R.string.action_remove
            }
        )

        Icon(
            imageVector = imageVector,
            tint = MaterialTheme.colors.onSurface,
            contentDescription = contentDescription
        )
    }
}
