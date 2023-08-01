package cz.damat.thebeercounter.repository

import androidx.room.withTransaction
import cz.damat.thebeercounter.room.AppDatabase
import cz.damat.thebeercounter.room.dao.HistoryItemDao
import cz.damat.thebeercounter.room.dao.ProductDao
import cz.damat.thebeercounter.room.entity.HistoryItem
import cz.damat.thebeercounter.room.entity.HistoryItemType
import cz.damat.thebeercounter.room.entity.InitialItemId
import cz.damat.thebeercounter.room.entity.Product
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent


/**
 * Created by MD on 29.12.22.
 */

class ProductRepository(private val db: AppDatabase, private val productDao: ProductDao, private val historyItemDao: HistoryItemDao) : KoinComponent {

    fun getShownProductsFlow() = productDao.getProductsFlow(true)

    //todo - use for allowing the user to delete currently not shown products
    fun getNotShownProductsFlow() = productDao.getProductsFlow(false)

    suspend fun saveProductAndIncrementCount(product: Product) {
        db.withTransaction {
            val newProductId = productDao.upsert(product)
            incrementProductCount(newProductId.toInt())
        }
    }

    suspend fun incrementProductCount(id: Int) {
        db.withTransaction {
            productDao.getProduct(id)?.let { product ->
                val count = product.count
                productDao.upsert(product.copy(count = count + 1))
                historyItemDao.upsert(
                    HistoryItem(
                        productId = id,
                        oldCount = count,
                        newCount = count + 1,
                        type = HistoryItemType.ADD
                    )
                )
            }
        }
    }

    suspend fun setProductCount(id: Int, count: Int, type: HistoryItemType) {
        db.withTransaction {
            productDao.getProduct(id)?.let { product ->
                val oldCount = product.count
                productDao.upsert(product.copy(count = count))
                historyItemDao.upsert(
                    HistoryItem(
                        productId = id,
                        oldCount = oldCount,
                        newCount = count,
                        type = type
                    )
                )
            }
        }
    }

    suspend fun hideProduct(id: Int) {
        db.withTransaction {
            productDao.getProduct(id)?.let { product ->
                val oldCount = product.count
                productDao.upsert(product.copy(shown = false, count = 0))
                historyItemDao.upsert(
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

    suspend fun clearAllAndAddInitialProduct(initialItemName: String) {
        db.withTransaction {
            getShownProductsFlow().first().forEach {
                if (it.id == InitialItemId) {
                    setProductCount(it.id, 0, HistoryItemType.DELETE)
                } else {
                    hideProduct(it.id)
                }
            }

            addInitialProduct(initialItemName)
        }
    }

    suspend fun addInitialProduct(name: String) {
        val initialProduct = Product(
            id = InitialItemId,
            name = name,
            price = null,
            count = 0,
            shown = true,
            suggested = true
        )
        productDao.upsert(initialProduct)
    }
}