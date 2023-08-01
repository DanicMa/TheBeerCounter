package cz.damat.thebeercounter.room.dao

import androidx.room.*
import cz.damat.thebeercounter.room.entity.Product
import kotlinx.coroutines.flow.Flow


/**
 * Created by MD on 23.04.23.
 */
@Dao
interface ProductDao : BaseDao<Product> {

    @Query("SELECT * FROM Product where id = :id")
    suspend fun getProduct(id: Int): Product?

    @Query("SELECT * FROM Product where shown = :shown")
    fun getProductsFlow(shown : Boolean): Flow<List<Product>>
}