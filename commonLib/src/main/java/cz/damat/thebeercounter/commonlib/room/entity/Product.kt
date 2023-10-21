package cz.damat.thebeercounter.commonlib.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


/**
 * Created by MD on 23.04.23.
 */

const val INITIAL_ITEM_ID = 1
const val ID = "id"

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    val name: String,
    val price : BigDecimal?,
    val count : Int = 0,
    val shown : Boolean = true,
    val suggested : Boolean = true,
)