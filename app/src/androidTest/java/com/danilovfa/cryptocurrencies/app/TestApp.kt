package com.danilovfa.cryptocurrencies.app

import android.app.Application
import com.danilovfa.cryptocurrencies.app.di.appModule
import com.danilovfa.cryptocurrencies.data.di.dataModule
import com.danilovfa.cryptocurrencies.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(
                appModule, dataModule, domainModule
            )
        }
    }
}