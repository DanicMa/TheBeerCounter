package cz.damat.thebeercounter.featureCounter.scene.edit

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.compose.component.CardThemed
import cz.damat.thebeercounter.commonUI.compose.component.ConfirmDialog
import cz.damat.thebeercounter.commonUI.compose.component.DropdownItem
import cz.damat.thebeercounter.commonUI.utils.Previews
import cz.damat.thebeercounter.commonUI.utils.collectCommand
import cz.damat.thebeercounter.commonUI.utils.collectStateWithLifecycle
import cz.damat.thebeercounter.commonUI.utils.getOnEvent
import cz.damat.thebeercounter.commonlib.room.entity.Product
import cz.damat.thebeercounter.featureCounter.scene.counter.dialog.AddNewProductDialog
import cz.damat.thebeercounter.featureCounter.scene.counter.dialog.SetCountDialog
import cz.damat.thebeercounter.commonUI.compose.theme.disabled
import cz.damat.thebeercounter.commonUI.compose.utils.vibrateStrong
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.text.NumberFormat
import java.util.*


/**
 * Created by MD on 07.11.23.
 */
@Composable
fun EditScreen(
    navController: NavController,
    productId : Int,
) {
    val viewModel: EditScreenViewModel = getViewModel() {
        parametersOf(productId)
    }
    val viewState = viewModel.collectStateWithLifecycle()
    val onEvent = viewModel.getOnEvent()

    CommandCollector(viewModel = viewModel, onEvent)
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
    onEvent: OnEvent
) {
    viewModel.collectCommand(block = { command ->
        when (command) {
            else -> {}
        }
    })
}


@Composable
private fun EditScreenContent(
    viewState: EditViewState,
    onEvent: OnEvent
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text("HELLO")
    }
}