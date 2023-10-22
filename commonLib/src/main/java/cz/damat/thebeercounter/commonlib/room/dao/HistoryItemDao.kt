package cz.damat.thebeercounter.commonlib.room.dao

import androidx.room.Dao
import androidx.room.Query
import cz.damat.thebeercounter.commonlib.room.BaseDao
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItem
import cz.damat.thebeercounter.commonlib.room.entity.Product
import kotlinx.coroutines.flow.Flow


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface HistoryItemDao : BaseDao<HistoryItem> {

    @Query("SELECT * FROM Product JOIN HistoryItem ON HistoryItem.productId = Product.id")
    fun getHistoryItemsMap(): Flow<Map<Product, List<HistoryItem>>>
}