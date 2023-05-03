package cz.damat.thebeercounter.common.utils

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


/**
 * Created by MD on 03.05.23.
 */
fun Date.formatForTimeOrDayAndTime(context : Context) : String {
    val isToday = DateUtils.isToday(this.time)

    val dateFormat = if (isToday) {
        DateFormat.getTimeFormat(context)
    } else {
        DateFormat.getMediumDateFormat(context)
    }

    return dateFormat.format(this)
}

fun Int.toStringSigned() : String {
    val plusMinusNF: NumberFormat = DecimalFormat("+#;-#")
    return plusMinusNF.format(this)
}