package cz.damat.thebeercounter

import android.app.Application
import cz.damat.thebeercounter.koin.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * Created by Matej Danicek on 29.12.22.
 */
class TheBeerCounterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TheBeerCounterApp)
            modules(koinModules)
        }
    }
}