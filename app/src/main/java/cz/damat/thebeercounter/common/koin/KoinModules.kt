package cz.damat.thebeercounter.common.koin

import cz.damat.thebeercounter.scene.counter.CounterScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by MD on 29.12.22.
 */
val appModule = module {

    viewModel {
        CounterScreenViewModel()
    }
}

val koinModules = listOf(appModule)