package cz.damat.thebeercounter.scene.counter.dialog

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.ui.component.DialogThemed
import cz.damat.thebeercounter.ui.utils.shake

@Composable
fun AddNewProductDialog(showDialog: MutableState<Boolean>, onNewProductCreated: (String) -> Unit) {
    if (!showDialog.value) return

    //todo - selection of existing products (autocomplete?) so that we don't have duplicates and history of products is consistent and usable for more advanced statistics in the future
    val textFieldValue = remember {
        mutableStateOf(TextFieldValue())
    }

    val shake = remember {
        mutableStateOf(false)
    }

    val focusRequester = remember { FocusRequester() }

    val onConfirmClick: () -> Unit = {
        val name = textFieldValue.value.text
        if (name.isEmpty()) {
            shake.value = true
        } else {
            onNewProductCreated(name)
            showDialog.value = false
        }
    }

    DialogThemed(
        showDialog = null,
        text = null,
        confirmString = stringResource(id = R.string.action_add),
        dismissString = stringResource(id = R.string.cancel),
        onConfirmClick = onConfirmClick,
        onDismissClick = {
            showDialog.value = false
        },
        content = {
            OutlinedTextField(
                label = {
                    Text(stringResource(id = R.string.name))
                },
                modifier = Modifier
                    .shake(shake)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, capitalization = KeyboardCapitalization.Sentences),
                keyboardActions = KeyboardActions(
                    onDone = {
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