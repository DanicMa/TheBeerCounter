package cz.damat.thebeercounter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.damat.thebeercounter.common.utils.baseCoroutineExceptionHandler
import cz.damat.thebeercounter.room.dao.ProductDao
import cz.damat.thebeercounter.room.model.HistoryItem
import cz.damat.thebeercounter.room.model.Product
import cz.damat.thebeercounter.room.typeconvertor.BigDecimalTypeConverter
import cz.damat.thebeercounter.room.typeconvertor.DateTypeConverter
import cz.damat.thebeercounter.room.typeconvertor.HistoryItemTypeTypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * Created by MD on 23.04.23.
 */
@Database(entities = [Product::class, HistoryItem::class], version = 1)
@TypeConverters(BigDecimalTypeConverter::class, DateTypeConverter::class, HistoryItemTypeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

class DatabaseCallback : RoomDatabase.Callback(), KoinComponent {

    private val productDao: ProductDao by inject()

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO + baseCoroutineExceptionHandler).launch {
            //todo
            productDao.saveProduct(Product(name = "Beer", price = 55.toBigDecimal()))
            productDao.saveProduct(Product(name = "Shots", price = 59.toBigDecimal()))
            productDao.saveProduct(Product(name = "Cocktail", price = 69.toBigDecimal()))
            productDao.saveProduct(Product(name = "Food", price = 55.toBigDecimal()))
//            productRepository.saveProduct(Product(name = "Shot", price =  69.toBigDecimal(), count =  1, shown = true, suggested = true))
        }
    }
}