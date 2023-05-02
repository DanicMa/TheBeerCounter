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
interface ProductDao {

    @Query("SELECT * FROM Product where id = :id")
    suspend fun getProduct(id: Int): Product?

    @Query("SELECT * FROM Product where shown = 1")
    fun getShownProductsFlow(): Flow<List<Product>>

    @Upsert
    suspend fun saveProduct(product: Product)
}