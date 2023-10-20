package cz.damat.thebeercounter.commonUI.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * Created by MD on 28.04.23.
 */
@Composable
fun CardThemed(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    borderColor: Color? = null,
    onClick: (() -> Unit)?,
    content: @Composable () -> Unit = {}
) {
    val roundedCornerShape = MaterialTheme.shapes.large

    Card(
        backgroundColor = backgroundColor,
        shape = roundedCornerShape,
        border = borderColor?.let { color -> BorderStroke(2.dp, color) },
        modifier = modifier
            .clip(roundedCornerShape) // clip so that ripple doesnt overflow in corners
            .then(
                onClick?.let {
                    Modifier.clickable(onClick = onClick)
                } ?: Modifier
            ),
        content = content
    )
}