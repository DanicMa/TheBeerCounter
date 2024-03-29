package cz.damat.thebeercounter.featureCounter.scene.history

import cz.damat.thebeercounter.commonUI.base.BaseViewModel
import cz.damat.thebeercounter.componentCounter.domain.repository.HistoryRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.time.LocalDate


/**
 * Created by MD on 03.05.23.
 */
class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : BaseViewModel<HistoryViewState, HistoryEvent, HistoryCommand>(HistoryViewState()) {

    init {
        historyRepository.getHistoryItemsFlow()
            .map { dayToHistoryItemsMap ->
                withContext(Dispatchers.Default) {// some non-trivial manipulations = dont do it on the main dispatcher
                    dayToHistoryItemsMap.map {
                        DayToHistoryDTO(
                            day = it.key,
                            historyItems = it.value.sortedByDescending { historyProduct -> historyProduct.historyItem.date }.toImmutableList(),
                            isExpanded = it.key == LocalDate.now()
                            // todo - correct updating of the isExpanded flag if there are any changes in the DB and flow fires multiple times OR replace flow with simple one-time query
                        )
                    }
                        .sortedByDescending { it.day }
                        .toImmutableList()
                }
            }
            .onEach {
                updateState { copy(items = it) }
            }
            .launchIn(viewModelScopeExHandled)
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