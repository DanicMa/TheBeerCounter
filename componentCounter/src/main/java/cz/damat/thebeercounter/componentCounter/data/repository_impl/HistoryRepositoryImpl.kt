package cz.damat.thebeercounter.componentCounter.data.repository_impl

import cz.damat.thebeercounter.commonlib.room.AppDatabase
import cz.damat.thebeercounter.commonlib.room.dao.HistoryItemDao
import cz.damat.thebeercounter.componentCounter.data.dto.HistoryProduct
import cz.damat.thebeercounter.componentCounter.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.map
import java.time.ZoneId


/**
 * Created by MD on 03.05.23.
 */
class HistoryRepositoryImpl(private val db: AppDatabase, private val historyItemDao: HistoryItemDao) : HistoryRepository {

    override fun getHistoryItemsFlow() = historyItemDao.getHistoryItemsMap().map {
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

