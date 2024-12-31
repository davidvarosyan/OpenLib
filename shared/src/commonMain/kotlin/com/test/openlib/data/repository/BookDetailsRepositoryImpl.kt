package com.test.openlib.data.repository

import com.test.openlib.domain.model.BookDetails
import com.test.openlib.domain.repository.BookDetailsRepository

class BookDetailsRepositoryImpl: BookDetailsRepository {
    override suspend fun getBookDetails(): BookDetails {
        TODO("Not yet implemented")
    }
}