package cz.damat.thebeercounter.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.ui.theme.medium


/**
 * @param showDialog - if null is provided, the dialog is always shown and the caller is responsible for showing and hiding the dialog
 * @param content - if provided, the [text] is ignored and the content is used to fill the main dialog area
 */
@Composable
fun DialogThemed(
    showDialog: MutableState<Boolean>?,
    title: String? = null,
    text: String? = null,
    content: @Composable (() -> Unit)? = null,
    confirmString: String = stringResource(id = R.string.ok),
    confirmTextColor: Color = MaterialTheme.colors.onSurface.medium,
    onConfirmClick: (() -> Unit),
    dismissString: String? = null,
    dismissTextColor: Color = MaterialTheme.colors.onSurface.medium,
    onDismissClick: (() -> Unit)? = null,
) {

    if (showDialog?.value != false) {
        val textContent: (@Composable () -> Unit)? = if (text == null && content == null) {
            null
        } else {
            {
                Column {
                    if (text != null) {
                        Text(text = text, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onSurface.medium)
                    }

                    if (content != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        content.invoke()
                    }
                }
            }
        }

        AlertDialog(
            modifier = Modifier.clip(MaterialTheme.shapes.medium),
            backgroundColor = MaterialTheme.colors.surface,
            onDismissRequest = {
                showDialog?.value = false
                onDismissClick?.invoke()
            },
            confirmButton = {
                DialogButton(
                    text = confirmString,
                    textColor = confirmTextColor
                ) {
                    showDialog?.value = false
                    onConfirmClick.invoke()
                }
            },
            dismissButton = dismissString?.let {
                {
                    DialogButton(
                        text = it,
                        textColor = dismissTextColor
                    ) {
                        showDialog?.value = false
                        onDismissClick?.invoke()
                    }
                }
            },
            title = title?.let {
                { Text(text = title, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSurface) }
            },
            text = textContent
        )
    }
}


@Composable
private fun DialogButton(text: String, textColor: Color, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp),
            text = text.uppercase(),
            color = textColor,
            style = MaterialTheme.typography.caption
        )
    }
}