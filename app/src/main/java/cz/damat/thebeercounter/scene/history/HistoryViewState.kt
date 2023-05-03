package cz.damat.thebeercounter.scene.history

import cz.damat.thebeercounter.common.base.ViewState
import cz.damat.thebeercounter.room.dto.HistoryProduct
import cz.damat.thebeercounter.room.entity.HistoryItem
import kotlinx.collections.immutable.ImmutableList


/**
 * Created by MD on 03.05.23.
 */
data class HistoryViewState(
    val items : ImmutableList<HistoryProduct>? = null,
) : ViewState
