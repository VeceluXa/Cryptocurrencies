package com.danilovfa.cryptocurrencies

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.danilovfa.cryptocurrencies.app.TestApp

class InstrumentationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}