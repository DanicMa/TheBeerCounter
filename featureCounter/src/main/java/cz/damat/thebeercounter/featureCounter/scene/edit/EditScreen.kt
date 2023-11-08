package cz.damat.thebeercounter.featureCounter.scene.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.compose.component.StateWrapper
import cz.damat.thebeercounter.commonUI.utils.Previews
import cz.damat.thebeercounter.commonUI.utils.collectCommand
import cz.damat.thebeercounter.commonUI.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.commonUI.utils.getOnEvent
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


/**
 * Created by MD on 07.11.23.
 */
@Composable
fun EditScreen(
    navController: NavController,
    productId: Int,
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
        StateWrapper(
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
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productName,
            onValueChange = { name -> onEvent(EditEvent.OnProductNameChange(name)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productCount,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            onValueChange = {
                 onEvent(EditEvent.OnProductCountChange(it))
            }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEvent(EditEvent.OnSaveClick) },
            content = {
                Text(text = stringResource(id = R.string.ok))
            }
        )
    }
}