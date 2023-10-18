package cz.damat.thebeercounter.commonlib

import androidx.room.Room
import cz.damat.thebeercounter.commonlib.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by MD on 18.10.23.
 */
val commonLibKoinModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "beer_counter.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}