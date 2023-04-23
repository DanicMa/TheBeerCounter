package cz.damat.thebeercounter.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import cz.damat.thebeercounter.room.model.Product
import kotlinx.coroutines.flow.Flow


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface ProductDao {

    @Query("SELECT * FROM Product where shown = 1")
    fun getShownProductsFlow(): Flow<List<Product>>

    @Upsert
    suspend fun saveProduct(product: Product)
}