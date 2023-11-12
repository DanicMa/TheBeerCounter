package cz.damat.thebeercounter.commonUI.compose.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * Created by MD on 09.11.23.
 */
@Composable
fun TBCButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .heightIn(min = 48.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
    ) {
        Text(text = text)
    }
}