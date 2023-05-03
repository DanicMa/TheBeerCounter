package cz.damat.thebeercounter.common.utils

import android.content.Context
import android.text.format.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


/**
 * Created by MD on 03.05.23.
 */
fun Date.formatToTimeString(context: Context): String {
    return DateFormat.getTimeFormat(context).format(this)
}

fun LocalDate.formatToDayString(): String {
    // todo - format without year if current year
    return format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.getDefault()))
}

fun Int.toStringSigned(): String {
    val plusMinusNF: NumberFormat = DecimalFormat("+#;-#")
    return plusMinusNF.format(this)
}