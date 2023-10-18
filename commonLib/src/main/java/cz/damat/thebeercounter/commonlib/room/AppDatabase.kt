package cz.damat.thebeercounter.commonlib.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.damat.thebeercounter.commonlib.room.dao.HistoryItemDao
import cz.damat.thebeercounter.commonlib.room.dao.ProductDao
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItem
import cz.damat.thebeercounter.commonlib.room.entity.Product
import cz.damat.thebeercounter.commonlib.room.typeconverter.BigDecimalTypeConverter
import cz.damat.thebeercounter.commonlib.room.typeconverter.DateTypeConverter
import cz.damat.thebeercounter.commonlib.room.typeconverter.HistoryItemTypeTypeConverter


/**
 * Created by MD on 23.04.23.
 */
@Database(entities = [Product::class, HistoryItem::class], version = 1)
@TypeConverters(BigDecimalTypeConverter::class, DateTypeConverter::class, HistoryItemTypeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun historyItemDao(): HistoryItemDao
}