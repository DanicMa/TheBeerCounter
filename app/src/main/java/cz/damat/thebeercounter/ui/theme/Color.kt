package cz.damat.thebeercounter.ui.theme

import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Gold = Color(0xFFFFD700)
val GoldDark = Color(0xFFD1B000)

val Color.medium: Color
    @Composable
    get() = this.copy(alpha = ContentAlpha.medium)

val Color.disabled: Color
    @Composable
    get() = this.copy(alpha = ContentAlpha.disabled)