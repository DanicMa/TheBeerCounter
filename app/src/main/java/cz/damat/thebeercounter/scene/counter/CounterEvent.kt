package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.ViewEvent


/**
 * Created by MD on 23.04.23.
 */
internal typealias OnEvent = (CounterEvent) -> Unit

sealed class CounterEvent : ViewEvent {
    object OnClearAllClicked : CounterEvent()
    object OnClearAllConfirmed : CounterEvent()
    data class OnProductClicked(val id: Int) : CounterEvent()
    data class OnMenuItemClicked(val menuItem: MenuItem, val id: Int) : CounterEvent()
    data class OnCountSet(val id: Int, val count : Int) : CounterEvent()
}
