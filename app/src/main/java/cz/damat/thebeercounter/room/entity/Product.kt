package cz.damat.thebeercounter.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


/**
 * Created by MD on 23.04.23.
 */

const val InitialItemId = 1
const val Id = "id"

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Id)
    val id: Int = 0,
    val name: String,
    val price : BigDecimal?,
    val count : Int = 0,
    val shown : Boolean = true,
    val suggested : Boolean = true,
)