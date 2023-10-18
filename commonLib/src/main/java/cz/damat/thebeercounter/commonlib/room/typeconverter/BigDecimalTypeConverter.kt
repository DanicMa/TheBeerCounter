package cz.damat.thebeercounter.commonlib.room.typeconverter

import androidx.room.TypeConverter
import java.math.BigDecimal


/**
 * Created by MD on 23.04.23.
 */
class BigDecimalTypeConverter {

    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String {
        return input?.toPlainString() ?: ""
    }

    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) return BigDecimal.valueOf(0.0)
        return input.toBigDecimalOrNull() ?: BigDecimal.valueOf(0.0)
    }

}