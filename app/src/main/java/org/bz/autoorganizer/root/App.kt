package org.bz.autoorganizer.root

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.bz.autoorganizer.BuildConfig
import org.bz.autoorganizer.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.logger.slf4jLogger
import timber.log.Timber

@ExperimentalCoroutinesApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi(this)
        initLogger()
    }

    private fun initDi(application: App) {
        startKoin {
            slf4jLogger(level = Level.DEBUG)
            if (BuildConfig.DEBUG) androidLogger(Level.ERROR)
            androidContext(application.applicationContext)
            modules(KoinModules.getAllModules())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}