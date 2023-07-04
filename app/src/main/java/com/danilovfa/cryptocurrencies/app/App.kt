package com.danilovfa.cryptocurrencies.app

import android.app.Application
import com.danilovfa.cryptocurrencies.data.di.dataModule
import com.danilovfa.cryptocurrencies.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(
                domainModule, dataModule
            ))
        }
    }
}