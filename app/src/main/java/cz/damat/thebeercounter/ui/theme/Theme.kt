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
    background = Color.Black,
    surface = Color(0xff212124)
)

private val LightColorPalette = lightColors(
    primary = GoldDark,
    primaryVariant = Gold,
    background = Color(0xffecf1fc),
    surface = Color.White,

    /* Other default colors to override
    secondary =
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@get:Composable
val Colors.disabled: Color
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