package cz.damat.thebeercounter.scene.history

import cz.damat.thebeercounter.common.base.BaseViewModel
import cz.damat.thebeercounter.repository.HistoryRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * Created by MD on 03.05.23.
 */
class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : BaseViewModel<HistoryViewState, HistoryEvent, HistoryCommand>(HistoryViewState()) {

    init {
        historyRepository.getHistoryItems()
            .onEach { updateState { copy(items = it.toImmutableList()) } }
            .launchIn(ioScope)
    }


    override fun onEvent(event: HistoryEvent) {
        when (event) {
            else -> {}
        }
    }

}