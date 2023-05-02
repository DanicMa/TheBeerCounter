package cz.damat.thebeercounter.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by MD on 28.04.23.
 */

private const val ProductId = "productId"

@Entity(
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf(Id),
        childColumns = arrayOf(ProductId),
        onDelete = ForeignKey.CASCADE
    )]
)

data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = ProductId, index = true)
    val productId: Int,
    val oldCount: Int,
    val newCount: Int,
    val date: Date =  Date(),
    val type: HistoryItemType,
)

enum class HistoryItemType(val id: Int) {
    ADD(1),
    RESET(2),
    DELETE(3),
    MANUAL(4),
}
