package com.test.openlib.data.repository

import com.test.openlib.domain.repository.NetworkRepository

class NetworkRepositoryImpl(private val networkHandler: NetworkHandler) : NetworkRepository {
    override fun isNetworkAvailable(): Boolean {
        return networkHandler.isNetworkAvailable()
    }

}