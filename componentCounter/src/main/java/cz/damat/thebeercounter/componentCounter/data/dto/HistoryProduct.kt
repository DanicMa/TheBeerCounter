package cz.damat.thebeercounter.componentCounter.data.dto

import androidx.compose.runtime.Immutable
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItem
import cz.damat.thebeercounter.commonlib.room.entity.Product

/**
 * Created by MD on 03.05.23.
 */
@Immutable
data class HistoryProduct(val historyItem: HistoryItem, val product: Product)