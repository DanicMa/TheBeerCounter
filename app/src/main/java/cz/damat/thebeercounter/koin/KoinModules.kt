package cz.damat.thebeercounter.koin

import cz.damat.thebeercounter.scene.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by Matej Danicek on 29.12.22.
 */
val appModule = module {

    viewModel {
        DashboardViewModel(get())
    }
}

val koinModules = listOf(appModule)