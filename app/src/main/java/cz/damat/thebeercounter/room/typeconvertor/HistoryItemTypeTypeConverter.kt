package cz.damat.thebeercounter.room.typeconvertor

import androidx.room.TypeConverter
import cz.damat.thebeercounter.room.entity.HistoryItemType


/**
 * Created by MD on 23.04.23.
 */
class HistoryItemTypeTypeConverter {

    @TypeConverter
    fun toDate(id: Int): HistoryItemType {
        return HistoryItemType.values().first() { it.id == id }
    }

    @TypeConverter
    fun fromDate(historyItemType: HistoryItemType): Int {
        return historyItemType.id;
    }

}