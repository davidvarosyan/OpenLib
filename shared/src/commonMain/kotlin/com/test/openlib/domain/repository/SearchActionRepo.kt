package com.test.openlib.domain.repository

import com.test.openlib.domain.model.SearchAction

interface SearchActionRepo {
    suspend fun getLastAction(): SearchAction?

    suspend fun saveLastAction(searchAction: SearchAction)
}