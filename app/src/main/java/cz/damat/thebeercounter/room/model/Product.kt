package cz.damat.thebeercounter.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal


/**
 * Created by MD on 23.04.23.
 */
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price : BigDecimal?,
    @ColumnInfo(name = "count")
    val count : Int = 0,
    @ColumnInfo(name = "shown")
    val shown : Boolean = true,
    @ColumnInfo(name = "suggested")
    val suggested : Boolean = true,
)