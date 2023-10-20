package cz.damat.thebeercounter.featureCounter.scene.history

import cz.damat.thebeercounter.commonUI.base.ViewEvent

/**
 * Created by MD on 03.05.23.
 */
internal typealias OnEvent = (HistoryEvent) -> Unit

sealed class HistoryEvent : ViewEvent {
    data class OnHistoryItemClick(val dayToHistoryDTO: DayToHistoryDTO) : HistoryEvent()
}