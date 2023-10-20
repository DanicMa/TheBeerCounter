package cz.damat.thebeercounter.commonlib.room.typeconverter

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by MD on 23.04.23.
 */
class DateTypeConverter {

    @TypeConverter
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

}