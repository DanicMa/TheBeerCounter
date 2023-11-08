package cz.damat.thebeercounter.componentCounter.data.repositoryImpl

import androidx.room.withTransaction
import cz.damat.thebeercounter.commonlib.room.AppDatabase
import cz.damat.thebeercounter.commonlib.room.dao.HistoryItemDao
import cz.damat.thebeercounter.commonlib.room.dao.ProductDao
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItem
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItemType
import cz.damat.thebeercounter.commonlib.room.entity.INITIAL_ITEM_ID
import cz.damat.thebeercounter.commonlib.room.entity.Product
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


/**
 * Created by MD on 29.12.22.
 */

class ProductRepositoryImpl(private val db: AppDatabase, private val productDao: ProductDao, private val historyItemDao: HistoryItemDao) : ProductRepository {

    override suspend fun getProduct(id: Int): Product? {
        return productDao.getProduct(id)
    }

    override fun getShownProductsFlow() = productDao.getProductsFlow(true)

    //todo - use for allowing the user to delete currently not shown products
    override fun getNotShownProductsFlow() = productDao.getProductsFlow(false)

    override suspend fun saveProductAndIncrementCount(product: Product) {
        db.withTransaction {
            val newProductId = productDao.upsert(product)
            incrementProductCount(newProductId.toInt())
        }
    }

    override suspend fun incrementProductCount(id: Int) {
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

    override suspend fun setProductCount(id: Int, count: Int, type: HistoryItemType) {
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

    override suspend fun updateProductNameAndSetCount(id: Int, name: String, count: Int) {
        db.withTransaction {
            productDao.getProduct(id)?.let { product ->
                val oldCount = product.count
                productDao.upsert(product.copy(name = name, count = count))

                if (oldCount != count) {
                    historyItemDao.upsert(
                        HistoryItem(
                            productId = id,
                            oldCount = oldCount,
                            newCount = count,
                            type = HistoryItemType.MANUAL
                        )
                    )
                }
            }
        }
    }

    override suspend fun hideProduct(id: Int) {
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

    override suspend fun clearAllAndAddInitialProduct(initialItemName: String) {
        db.withTransaction {
            getShownProductsFlow().first().forEach {
                if (it.id == INITIAL_ITEM_ID) {
                    setProductCount(it.id, 0, HistoryItemType.DELETE)
                } else {
                    hideProduct(it.id)
                }
            }

            addInitialProduct(initialItemName)
        }
    }

    override suspend fun addInitialProduct(name: String) {
        val initialProduct = Product(
            id = INITIAL_ITEM_ID,
            name = name,
            price = null,
            count = 0,
            shown = true,
            suggested = true
        )
        productDao.upsert(initialProduct)
    }
}