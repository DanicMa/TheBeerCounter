package cz.damat.thebeercounter.common.koin

import androidx.room.Room
import cz.damat.thebeercounter.componentCounter.domain.repository.HistoryRepository
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import cz.damat.thebeercounter.componentCounter.data.repository_impl.HistoryRepositoryImpl
import cz.damat.thebeercounter.componentCounter.data.repository_impl.ProductRepositoryImpl
import cz.damat.thebeercounter.commonlib.room.AppDatabase
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