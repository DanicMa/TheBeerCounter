package cz.damat.thebeercounter.common.koin

import androidx.room.Room
import cz.damat.thebeercounter.repository.HistoryRepository
import cz.damat.thebeercounter.repository.ProductRepository
import cz.damat.thebeercounter.room.AppDatabase
import cz.damat.thebeercounter.scene.counter.CounterScreenViewModel
import cz.damat.thebeercounter.scene.history.HistoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by MD on 29.12.22.
 */
val appModule = module {
    viewModel {
        CounterScreenViewModel(get(), androidApplication().resources)
    }

    viewModel {
        HistoryViewModel(get())
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "beer_counter.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().historyItemDao() }

    single { ProductRepository(get(), get(), get()) }
    single { HistoryRepository(get(), get()) }
}

val koinModules = listOf(appModule, databaseModule)