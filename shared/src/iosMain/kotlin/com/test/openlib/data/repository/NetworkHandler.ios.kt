package com.test.openlib.data.repository

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags

actual class NetworkHandler {
    @OptIn(ExperimentalForeignApi::class)
    actual fun isNetworkAvailable(): Boolean {
        val reachability = SCNetworkReachabilityCreateWithName(null, "google.com") ?: return false
        memScoped {
            val flags = alloc<UIntVar>()
            val success = SCNetworkReachabilityGetFlags(reachability, flags.ptr)
            if (!success) return false

            val reachable = flags.value and kSCNetworkReachabilityFlagsReachable != 0u
            val requiresConnection =
                flags.value and kSCNetworkReachabilityFlagsConnectionRequired != 0u
            return reachable && !requiresConnection
        }
    }

    companion object {
        private const val kSCNetworkReachabilityFlagsReachable = 0x2u
        private const val kSCNetworkReachabilityFlagsConnectionRequired = 0x4u
    }


}