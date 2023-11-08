package cz.damat.thebeercounter.commonUI.base

import androidx.annotation.StringRes


/**
 * Created by MD on 08.11.23.
 */
sealed class State {
    object Loading : State()
    object Content : State()
    data class Error(@StringRes val message: Int) : State()
}
