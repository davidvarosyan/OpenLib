package com.test.openlib.domain.usecase

import com.test.openlib.domain.model.SearchAction
import com.test.openlib.domain.repository.SearchActionRepo

interface SearchActionUseCase {
    suspend fun saveLastAction(searchAction: SearchAction)

    suspend fun getLastAction(): SearchAction?
}

class SearchActionUseCaseImpl(private val searchActionRepo: SearchActionRepo) :
    SearchActionUseCase {
    override suspend fun saveLastAction(searchAction: SearchAction) {
        searchActionRepo.saveLastAction(searchAction)
    }

    override suspend fun getLastAction(): SearchAction? {
        return searchActionRepo.getLastAction()
    }
}

