package cz.damat.thebeercounter.commonlib.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by MD on 28.04.23.
 */

const val PRODUCT_ID_COLUMN = "productId"

@Entity(
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf(ID),
        childColumns = arrayOf(PRODUCT_ID_COLUMN),
        onDelete = ForeignKey.CASCADE
    )]
)

data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = PRODUCT_ID_COLUMN, index = true)
    val productId: Int,
    val oldCount: Int,
    val newCount: Int,
    val date: Date =  Date(),
    val type: HistoryItemType,
)

@Suppress("MagicNumber")
enum class HistoryItemType(val id: Int) {
    ADD(1),
    RESET(2),
    DELETE(3),
    MANUAL(4),
}
