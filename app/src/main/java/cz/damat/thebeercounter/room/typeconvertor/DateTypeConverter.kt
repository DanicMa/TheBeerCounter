package cz.damat.thebeercounter.room.typeconvertor

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.Date

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
        return date.time;
    }

}