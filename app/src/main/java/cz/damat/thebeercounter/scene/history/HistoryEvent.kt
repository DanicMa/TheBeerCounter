package cz.damat.thebeercounter.scene.history

import cz.damat.thebeercounter.common.base.ViewEvent

/**
 * Created by MD on 03.05.23.
 */
internal typealias OnEvent = (HistoryEvent) -> Unit

sealed class HistoryEvent : ViewEvent {
}