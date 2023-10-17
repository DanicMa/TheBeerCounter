package cz.damat.thebeercounter.commonUI.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler


/**
 * Created by MD on 23.04.23.
 */
val baseCoroutineExceptionHandler = CoroutineExceptionHandler { _, ex ->
    if (ex !is CancellationException) {
        throw ex
    }
}