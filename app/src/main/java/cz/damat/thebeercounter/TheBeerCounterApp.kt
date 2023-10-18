package cz.damat.thebeercounter

import android.app.Application
import cz.damat.thebeercounter.common.koin.appModule
import cz.damat.thebeercounter.commonlib.commonLibKoinModule
import cz.damat.thebeercounter.componentCounter.componentCounterKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * Created by MD on 29.12.22.
 */
class TheBeerCounterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TheBeerCounterApp)
            modules(listOf(appModule, commonLibKoinModule, componentCounterKoinModule))
        }
    }
}