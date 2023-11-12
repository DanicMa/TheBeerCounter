package cz.damat.thebeercounter.commonUI.compose.component

import android.view.KeyEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.damat.thebeercounter.commonUI.compose.utils.applyIf


/**
 * Created by MD on 09.11.23.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TBCTextField(
    modifier: Modifier = Modifier,
    label: String?,
    value: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    error: String? = null,
    singleLine: Boolean = false,
    hasNextDownImeAction: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
    onValueChanged: (String) -> Unit,
) {
    // mutable state that gets updated "twice" is necessary otherwise cursors skips when writing fast
    var valueState by remember(value) {
        mutableStateOf(value)
    }

    val focusManager = LocalFocusManager.current

    val textFieldShape = MaterialTheme.shapes.medium
    OutlinedTextField(
        modifier = modifier
            .clip(textFieldShape)
            .applyIf(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .applyIf(hasNextDownImeAction) {
                onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                }
            },
        shape = textFieldShape,
        value = valueState,
        onValueChange = {
            valueState = it
            onValueChanged(it)
        },
        label = {
            if (label != null) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            }
        },
        readOnly = readOnly,
        enabled = enabled,
        isError = error != null,
        singleLine = singleLine,
        placeholder = {
            Text(
                text = placeholder ?: "",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Normal),
                textAlign = TextAlign.Start
            )
        },
        textStyle = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Normal),
        keyboardOptions = if (hasNextDownImeAction) keyboardOptions.copy(imeAction = ImeAction.Next) else keyboardOptions,
        keyboardActions = if (hasNextDownImeAction) {
            KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        } else {
            keyboardActions
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        interactionSource = interactionSource,
    )

    if (error != null) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = error,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.error,
        )
    }
}