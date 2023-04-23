package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.ViewEvent


/**
 * Created by MD on 23.04.23.
 */
internal typealias OnEvent = (CounterEvent) -> Unit

sealed class CounterEvent : ViewEvent
