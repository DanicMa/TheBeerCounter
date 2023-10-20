package cz.damat.thebeercounter.commonlib.room


/**
 * Created by MD on 06.06.23.
 */
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Collection<T>): List<Long>

    @Upsert
    suspend fun upsert(entity: T): Long

    @Upsert
    suspend fun upsert(entity: Collection<T>): List<Long>

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun update(entity: Collection<T>)

    @Delete
    suspend fun delete(entity: T)

    @Delete
    suspend fun delete(entity: List<T>)

}