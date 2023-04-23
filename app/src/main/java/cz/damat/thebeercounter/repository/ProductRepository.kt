package cz.damat.thebeercounter.repository

import cz.damat.thebeercounter.room.dao.ProductDao
import cz.damat.thebeercounter.room.model.Product
import org.koin.core.component.KoinComponent


/**
 * Created by MD on 29.12.22.
 */
class ProductRepository(val productDao: ProductDao) : KoinComponent {
    //todo

    fun getShownProductsFlow() = productDao.getShownProductsFlow()

    suspend fun saveProduct(product: Product) = productDao.saveProduct(product)
}