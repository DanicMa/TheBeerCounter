package cz.damat.thebeercounter.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import cz.damat.thebeercounter.room.model.HistoryItem
import cz.damat.thebeercounter.room.model.HistoryItemType
import cz.damat.thebeercounter.room.model.Product
import kotlinx.coroutines.flow.Flow
import java.util.Date


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface ProductDao {

    @Query("SELECT * FROM Product where id = :id")
    suspend fun getProduct(id: Int): Product?

    @Query("SELECT * FROM Product where shown = 1")
    fun getShownProductsFlow(): Flow<List<Product>>

    @Upsert
    suspend fun saveProduct(product: Product)

    @Upsert
    suspend fun saveHistoryItem(historyItem: HistoryItem)

    @Transaction
    suspend fun incrementProductCount(id: Int) {
        getProduct(id)?.let { product ->
            val count = product.count
            saveProduct(product.copy(count = count + 1))
            saveHistoryItem(
                HistoryItem(
                    productId = id,
                    oldCount = count,
                    newCount = count + 1,
                    type = HistoryItemType.ADD
                )
            )
        }
    }

    @Transaction
    suspend fun setProductCount(id: Int, count: Int, type: HistoryItemType) {
        getProduct(id)?.let { product ->
            val oldCount = product.count
            saveProduct(product.copy(count = count))
            saveHistoryItem(
                HistoryItem(
                    productId = id,
                    oldCount = oldCount,
                    newCount = count,
                    type = type
                )
            )
        }
    }

    @Transaction
    suspend fun hideProduct(id: Int) {
        getProduct(id)?.let { product ->
            val oldCount = product.count
            saveProduct(product.copy(shown = false, count = 0))
            saveHistoryItem(
                HistoryItem(
                    productId = id,
                    oldCount = oldCount,
                    newCount = 0,
                    type = HistoryItemType.DELETE
                )
            )
        }
    }
}