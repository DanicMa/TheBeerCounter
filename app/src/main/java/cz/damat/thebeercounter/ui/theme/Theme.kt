package cz.damat.thebeercounter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    //todo
    primary = Gold,
    primaryVariant = GoldDark,
)

private val LightColorPalette = lightColors(
    primary = GoldDark,
    primaryVariant = Gold,

    /* Other default colors to override
    secondary =
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@get:Composable
val Colors.disabled : Color
    get() = DisabledGrey //if (isLight) DisabledGrey else DisabledGrey


@Composable
fun TheBeerCounterTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}