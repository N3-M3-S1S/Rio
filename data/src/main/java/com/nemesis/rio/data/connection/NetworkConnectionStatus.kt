package com.nemesis.rio.data.connection

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkConnectionStatus(private val connectivityManager: ConnectivityManager) {

    fun isDeviceConnectedToNetwork(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        return isNetworkHasConnection(activeNetwork)
    }

    private fun isNetworkHasConnection(network: Network): Boolean {
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        val networkTransports = getNetworkTransports()
        return networkTransports.any(networkCapabilities::hasTransport)
    }

    private fun getNetworkTransports() =
        arrayOf(NetworkCapabilities.TRANSPORT_WIFI, NetworkCapabilities.TRANSPORT_CELLULAR)
}
