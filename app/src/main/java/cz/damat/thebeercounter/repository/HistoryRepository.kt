package cz.damat.thebeercounter.repository

import cz.damat.thebeercounter.room.AppDatabase
import cz.damat.thebeercounter.room.dao.HistoryItemDao
import cz.damat.thebeercounter.room.dto.HistoryProduct
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import java.time.ZoneId


/**
 * Created by MD on 03.05.23.
 */
class HistoryRepository(private val db: AppDatabase, private val historyItemDao: HistoryItemDao) : KoinComponent {

    fun getHistoryItems() = historyItemDao.getHistoryItemsMap().map {
        val flatList = it.flatMap { (product, historyItems) ->
            historyItems.map { historyItem ->
                HistoryProduct(historyItem, product)
            }
        }

        return@map flatList.groupBy { historyProduct ->
            historyProduct.historyItem.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }
}

