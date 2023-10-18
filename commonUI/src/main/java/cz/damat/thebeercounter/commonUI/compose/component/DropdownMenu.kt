package cz.damat.thebeercounter.commonUI.compose.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import cz.damat.thebeercounter.commonUI.compose.theme.disabled
import kotlinx.coroutines.launch


/**
 * Created by MD on 28.04.23.
 *
 *  HAS TO BE IN THE SAME PARENT AS THE ANCHORING VIEW NEAR WHICH THE DROPDOWN IS TO BE SHOWN.
 * e.g. has to be in the same box as the button opening the dropdown
 */
@Composable
fun DropdownMenu(
    dropdownShown: MutableState<Boolean>,
    scaffoldState: ScaffoldState?,
    items: List<DropdownItem>
) {
    val coroutineScope = rememberCoroutineScope()

    Dropdown(dropdownShown = dropdownShown) {
        items.forEach { item ->
            DropdownMenuItem(onClick = {
                dropdownShown.value = false

                if (item.enabled) {
                    item.onClick.invoke()
                } else {
                    item.disabledMessage?.let { disabledMessage ->
                        coroutineScope.launch {
                            scaffoldState?.snackbarHostState?.showSnackbar(disabledMessage, actionLabel = null, SnackbarDuration.Short)
                        }
                    }
                }
            }) {

                val textColor = (item.textColor ?: MaterialTheme.colors.onSurface).let {
                    if (item.enabled) {
                        it
                    } else {
                        it.disabled
                    }
                }

                Text(
                    text = item.text,
                    style = MaterialTheme.typography.caption,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun Dropdown(dropdownShown: MutableState<Boolean>, content: @Composable () -> Unit) {
    DropdownMenu(
        expanded = dropdownShown.value,
        onDismissRequest = { dropdownShown.value = false },
    ) {
        content.invoke()
    }
}

data class DropdownItem(
    val text: String,
    val textColor: Color? = null,
    val enabled: Boolean = true,
    val disabledMessage: String? = null,
    val onClick: () -> Unit
)