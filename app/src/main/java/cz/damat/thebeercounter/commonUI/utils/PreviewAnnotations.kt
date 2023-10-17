package cz.damat.thebeercounter.commonUI.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "A: Light Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "B: Dark Mode")
annotation class Previews

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "A: Light Mode", widthDp = 500, heightDp = 1_000)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "B: Dark Mode", widthDp = 500, heightDp = 1_000)
annotation class PreviewsLarge

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
annotation class PreviewLightMode

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
annotation class PreviewDarkMode