package cz.damat.thebeercounter.repository

import cz.damat.thebeercounter.room.dao.ProductDao
import cz.damat.thebeercounter.room.model.HistoryItemType
import cz.damat.thebeercounter.room.model.Product
import org.koin.core.component.KoinComponent


/**
 * Created by MD on 29.12.22.
 */
class ProductRepository(val productDao: ProductDao) : KoinComponent {

    fun getShownProductsFlow() = productDao.getShownProductsFlow()

    suspend fun saveProduct(product: Product) = productDao.saveProduct(product)

    suspend fun incrementProductCount(id: Int) {
        productDao.incrementProductCount(id)
    }

    suspend fun setProductCount(id: Int, count: Int) {
        productDao.setProductCount(id, count, HistoryItemType.MANUAL)
    }

    suspend fun resetProductCount(id: Int) {
        productDao.setProductCount(id, 0, HistoryItemType.RESET)
    }

    suspend fun hideProduct(id: Int) {
        productDao.hideProduct(id)
    }
}