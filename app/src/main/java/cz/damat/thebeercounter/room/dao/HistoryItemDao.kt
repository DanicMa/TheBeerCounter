package cz.damat.thebeercounter.room.dao

import androidx.room.*
import cz.damat.thebeercounter.room.entity.HistoryItem
import cz.damat.thebeercounter.room.entity.Product
import kotlinx.coroutines.flow.Flow


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface HistoryItemDao {

    @Upsert
    suspend fun saveHistoryItem(historyItem: HistoryItem) : Long

    @Query("SELECT * FROM Product JOIN HistoryItem ON HistoryItem.productId = Product.id")
    fun getHistoryItemsMap(): Flow<Map<Product, List<HistoryItem>>>
}