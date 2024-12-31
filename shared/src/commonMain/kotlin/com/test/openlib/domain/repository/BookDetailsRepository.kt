package com.test.openlib.domain.repository

import com.test.openlib.domain.model.BookDetails

interface BookDetailsRepository {
    suspend fun getBookDetails(): BookDetails
}