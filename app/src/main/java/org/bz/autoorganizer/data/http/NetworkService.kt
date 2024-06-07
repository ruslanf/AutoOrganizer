package org.bz.autoorganizer.data.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import timber.log.Timber

class NetworkService(private val context: Context) {

    private var wifiManager: WifiManager? = null
    private var connectivityManager: ConnectivityManager? = null

    init {
        initializeWithApplicationContext()
    }

    // Helper that detects if online
    fun isOnline(): Boolean {
        val capabilities = connectivityManager?.activeNetwork?.let { network ->
            connectivityManager?.getNetworkCapabilities(network)
        }
        return capabilities?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false
    }

    private fun initializeWithApplicationContext() {
        Timber.v("initializeWithApplicationContext()...")
        wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}