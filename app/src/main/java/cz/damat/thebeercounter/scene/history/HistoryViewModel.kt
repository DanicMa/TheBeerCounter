package cz.damat.thebeercounter.scene.history

import cz.damat.thebeercounter.common.base.BaseViewModel
import cz.damat.thebeercounter.repository.HistoryRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


/**
 * Created by MD on 03.05.23.
 */
class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : BaseViewModel<HistoryViewState, HistoryEvent, HistoryCommand>(HistoryViewState()) {

    init {
        historyRepository.getHistoryItems()
            .map { dayToHistoryItemsMap ->
                dayToHistoryItemsMap.map {
                    DayToHistoryDTO(
                        day = it.key,
                        historyItems = it.value.sortedByDescending { historyProduct -> historyProduct.historyItem.date }.toImmutableList(),
                        isExpanded = false
                    )
                }
                    .sortedByDescending { it.day }
                    .toImmutableList()
            }
            .onEach { updateState { copy(items = it) } }
            .launchIn(ioScope)
    }


    override fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.OnHistoryItemClick -> onHistoryItemClick(event.dayToHistoryDTO)
        }
    }

    private fun onHistoryItemClick(dayToHistoryDTO: DayToHistoryDTO) {
        updateState {
            copy(
                items = items?.map {
                    if (it.day == dayToHistoryDTO.day) {
                        it.copy(isExpanded = !it.isExpanded)
                    } else {
                        it
                    }
                }?.toImmutableList()
            )
        }
    }
}