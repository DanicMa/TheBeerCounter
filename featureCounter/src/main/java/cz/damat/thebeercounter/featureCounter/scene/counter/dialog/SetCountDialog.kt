package cz.damat.thebeercounter.featureCounter.scene.counter.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import cz.damat.thebeercounter.commonlib.room.entity.Product
import cz.damat.thebeercounter.featureCounter.scene.counter.CounterEvent
import cz.damat.thebeercounter.featureCounter.scene.counter.OnEvent
import cz.damat.thebeercounter.commonUI.compose.component.DialogThemed
import cz.damat.thebeercounter.commonUI.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetCountDialog(productState: MutableState<Product?>, onEvent: OnEvent) {
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
        confirmString = stringResource(id = R.string.action_set),
        dismissString = stringResource(id = R.string.cancel),
        onConfirmClick = onConfirmClick,
        onDismissClick = {
            productState.value = null
        },
        content = {
            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                label = {
                    Text(stringResource(id = R.string.count))
                },
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