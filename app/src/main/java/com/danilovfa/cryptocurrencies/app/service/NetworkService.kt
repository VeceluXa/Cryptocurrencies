package com.danilovfa.cryptocurrencies.app.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.danilovfa.cryptocurrencies.utils.NetworkStatus
import java.util.Timer
import java.util.TimerTask

class NetworkService: Service() {
    private val binder = LocalBinder()
    private val timer = Timer()
    private val interval = 13 * 1000L
    private val networkStatus = NetworkStatus(this)

    private var internetCheckListener: InternetCheckListener? = null

    inner class LocalBinder : Binder() {
        fun getService(): NetworkService = this@NetworkService
    }

    interface InternetCheckListener {
        fun onInternetStatusChanged(isConnected: Boolean)
    }

    fun setInternetCheckListener(listener: InternetCheckListener) {
        internetCheckListener = listener
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    private fun startTimer() {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val isConnected = networkStatus.checkInternetConnection()
                internetCheckListener?.onInternetStatusChanged(isConnected)
            }
        }, 0, interval)
    }

    private fun stopTimer() {
        timer.cancel()
    }
}