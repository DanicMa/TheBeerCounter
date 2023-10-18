package cz.damat.thebeercounter.componentCounter.domain.repository

import cz.damat.thebeercounter.componentCounter.data.dto.HistoryProduct
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import java.time.LocalDate


/**
 * Created by MD on 03.05.23.
 */
interface HistoryRepository : KoinComponent {
    fun getHistoryItemsFlow() : Flow<Map<LocalDate, List<HistoryProduct>>>
}

