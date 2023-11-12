package cz.damat.thebeercounter.commonUI.compose.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed


/**
 * Created by MD on 09.11.23.
 */
@SuppressLint("UnnecessaryComposedModifier") // is actually necessary
fun Modifier.applyIf(condition: Boolean, modify: @Composable Modifier.() -> Modifier): Modifier = composed {
    if (condition) {
        this.modify()
    } else {
        this
    }
}