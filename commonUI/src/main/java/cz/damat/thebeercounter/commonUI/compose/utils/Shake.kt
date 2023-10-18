package cz.damat.thebeercounter.commonUI.compose.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Created by MD on 03.05.23.
 */
private const val ShakeDurationMillis = 800

fun Modifier.shake(enabled: MutableState<Boolean>) = composed {
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val keyframes = keyframes {
        durationMillis = ShakeDurationMillis
        val easing = FastOutLinearInEasing

        // generate 8 keyframes
        for (i in 1..8) {
            val x = when (i % 3) {
                0 -> 8f
                1 -> -8f
                else -> 0f
            }
            x at durationMillis / 10 * i with easing
        }
    }

    LaunchedEffect(enabled.value) {
        if (enabled.value) {
            offsetX.animateTo(
                targetValue = 0f,
                animationSpec = keyframes,
            )

            //todo - get the actual animation end so that we can correctly reset the enabled state variable
            coroutineScope.launch {
                delay(ShakeDurationMillis.toLong())
                enabled.value = false
            }
        }
    }

    offset(offsetX.value.dp, 0.dp)
}