package cz.damat.thebeercounter.room.dto

import androidx.compose.runtime.Immutable
import cz.damat.thebeercounter.room.entity.HistoryItem
import cz.damat.thebeercounter.room.entity.Product

/**
 * Created by MD on 03.05.23.
 */
@Immutable
data class HistoryProduct(val historyItem: HistoryItem, val product: Product)