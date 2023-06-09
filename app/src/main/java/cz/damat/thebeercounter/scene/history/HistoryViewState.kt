package cz.damat.thebeercounter.scene.history

import androidx.compose.runtime.Immutable
import cz.damat.thebeercounter.common.base.ViewState
import cz.damat.thebeercounter.room.dto.HistoryProduct
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
