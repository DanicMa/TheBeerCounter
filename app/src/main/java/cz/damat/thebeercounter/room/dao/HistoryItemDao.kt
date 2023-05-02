package cz.damat.thebeercounter.room.dao

import androidx.room.*
import cz.damat.thebeercounter.room.model.HistoryItem
import cz.damat.thebeercounter.room.model.HistoryItemType
import cz.damat.thebeercounter.room.model.InitialItemId
import cz.damat.thebeercounter.room.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface HistoryItemDao {

    @Upsert
    suspend fun saveHistoryItem(historyItem: HistoryItem)
}