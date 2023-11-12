package cz.damat.thebeercounter.featureCounter

import cz.damat.thebeercounter.featureCounter.scene.counter.CounterScreenViewModel
import cz.damat.thebeercounter.featureCounter.scene.edit.EditScreenViewModel
import cz.damat.thebeercounter.featureCounter.scene.history.HistoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by MD on 18.10.23.
 */
val featureCounterKoinModule = module {
    viewModel {
        CounterScreenViewModel(get(), androidApplication().resources)
    }

    viewModel { (productId: Int) ->
        EditScreenViewModel(productId, get())
    }

    viewModel {
        HistoryViewModel(get())
    }
}