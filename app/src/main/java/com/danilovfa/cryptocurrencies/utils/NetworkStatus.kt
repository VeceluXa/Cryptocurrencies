package com.danilovfa.cryptocurrencies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatus(private val context: Context) {
    fun checkInternetConnection(): Boolean {
        var isConnected = false
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            isConnected = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        return isConnected
    }

}