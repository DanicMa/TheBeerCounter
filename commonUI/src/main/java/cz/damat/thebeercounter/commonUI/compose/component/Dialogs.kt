package cz.damat.thebeercounter.commonUI.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cz.damat.thebeercounter.commonUI.compose.theme.medium
import cz.damat.thebeercounter.commonUI.R


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

        Dialog(onDismissRequest = {
            showDialog?.value = false
            onDismissClick?.invoke()
        }) {
            Surface(shape = MaterialTheme.shapes.medium) {
                Column {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        title?.let {
                            Spacer(Modifier.size(8.dp))
                            Text(text = title, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSurface)
                            Spacer(Modifier.size(16.dp))
                        }

                        textContent?.invoke()
                    }

                    Spacer(Modifier.size(4.dp))

                    Row(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        Arrangement.spacedBy(8.dp, Alignment.End),
                    ) {
                        // dismiss button
                        dismissString?.let {
                            DialogButton(
                                text = it,
                                textColor = dismissTextColor
                            ) {
                                showDialog?.value = false
                                onDismissClick?.invoke()
                            }
                        }

                        // confirm button
                        DialogButton(
                            text = confirmString,
                            textColor = confirmTextColor
                        ) {
                            showDialog?.value = false
                            onConfirmClick.invoke()
                        }
                    }
                }
            }
        }
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

@Composable
fun ConfirmDialog(
    showDialog: MutableState<Boolean>?,
    title: String?,
    text: String?,
    content: @Composable (() -> Unit)? = null,
    confirmString: String,
    confirmTextColor: Color = MaterialTheme.colors.onSurface.medium,
    onConfirmClick: (() -> Unit),
    dismissString: String? = stringResource(id = R.string.cancel),
    dismissTextColor: Color = MaterialTheme.colors.onSurface.medium,
    onDismissClick: (() -> Unit)? = null,
) {
    DialogThemed(
        showDialog = showDialog,
        title = title,
        text = text,
        content = content,
        confirmString = confirmString,
        confirmTextColor = confirmTextColor,
        onConfirmClick = onConfirmClick,
        dismissString = dismissString,
        dismissTextColor = dismissTextColor,
        onDismissClick = onDismissClick
    )
}