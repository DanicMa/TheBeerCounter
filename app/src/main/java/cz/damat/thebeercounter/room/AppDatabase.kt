package cz.damat.thebeercounter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.damat.thebeercounter.common.utils.baseCoroutineExceptionHandler
import cz.damat.thebeercounter.room.dao.HistoryItemDao
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
    abstract fun historyItemDao(): HistoryItemDao
}