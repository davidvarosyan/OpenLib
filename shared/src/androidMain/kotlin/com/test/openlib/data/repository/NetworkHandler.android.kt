package com.test.openlib.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

actual class NetworkHandler(private val context: Context) {
    actual fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}