package cz.damat.thebeercounter.componentCounter.domain.repository

import cz.damat.thebeercounter.commonlib.room.AppDatabase
import cz.damat.thebeercounter.commonlib.room.dao.HistoryItemDao
import cz.damat.thebeercounter.commonlib.room.dao.ProductDao
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItem
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItemType
import cz.damat.thebeercounter.commonlib.room.entity.InitialItemId
import cz.damat.thebeercounter.commonlib.room.entity.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent


/**
 * Created by MD on 29.12.22.
 */

interface ProductRepository : KoinComponent {

    fun getShownProductsFlow(): Flow<List<Product>>

    //todo - use for allowing the user to delete currently not shown products
    fun getNotShownProductsFlow(): Flow<List<Product>>

    suspend fun saveProductAndIncrementCount(product: Product)

    suspend fun incrementProductCount(id: Int)

    suspend fun setProductCount(id: Int, count: Int, type: HistoryItemType)

    suspend fun hideProduct(id: Int)

    suspend fun clearAllAndAddInitialProduct(initialItemName: String)

    suspend fun addInitialProduct(name: String)
}