package cz.damat.thebeercounter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.damat.thebeercounter.room.dao.HistoryItemDao
import cz.damat.thebeercounter.room.dao.ProductDao
import cz.damat.thebeercounter.room.entity.HistoryItem
import cz.damat.thebeercounter.room.entity.Product
import cz.damat.thebeercounter.room.typeconvertor.BigDecimalTypeConverter
import cz.damat.thebeercounter.room.typeconvertor.DateTypeConverter
import cz.damat.thebeercounter.room.typeconvertor.HistoryItemTypeTypeConverter


/**
 * Created by MD on 23.04.23.
 */
@Database(entities = [Product::class, HistoryItem::class], version = 1)
@TypeConverters(BigDecimalTypeConverter::class, DateTypeConverter::class, HistoryItemTypeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun historyItemDao(): HistoryItemDao
}