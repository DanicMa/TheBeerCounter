package cz.damat.thebeercounter.featureCounter.scene.history

import androidx.compose.runtime.Immutable
import cz.damat.thebeercounter.commonUI.base.ViewState
import cz.damat.thebeercounter.componentCounter.data.dto.HistoryProduct
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate


/**
 * Created by MD on 03.05.23.
 */
data class HistoryViewState(
    val items : ImmutableList<DayToHistoryDTO>? = null,
) : ViewState

@Immutable
data class DayToHistoryDTO(
    val day: LocalDate,
    val historyItems: ImmutableList<HistoryProduct>,
    val isExpanded: Boolean = false,
)
