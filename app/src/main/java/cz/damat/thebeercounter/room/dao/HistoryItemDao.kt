package cz.damat.thebeercounter.room.dao

import androidx.room.Dao
import androidx.room.Upsert
import cz.damat.thebeercounter.room.model.HistoryItem


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface HistoryItemDao {

    @Upsert
    suspend fun saveHistoryItem(historyItem: HistoryItem)
}