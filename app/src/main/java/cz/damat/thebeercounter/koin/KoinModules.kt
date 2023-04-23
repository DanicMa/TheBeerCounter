package cz.damat.thebeercounter.koin

import cz.damat.thebeercounter.scene.counter.CounterScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by Matej Danicek on 29.12.22.
 */
val appModule = module {

    viewModel {
        CounterScreenViewModel()
    }
}

val koinModules = listOf(appModule)