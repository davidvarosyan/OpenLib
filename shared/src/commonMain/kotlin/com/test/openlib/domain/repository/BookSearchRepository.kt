package com.test.openlib.domain.repository

import com.test.openlib.domain.model.Book

interface BookSearchRepository {
    suspend fun getBooks(subject: String, page: Int, offset: Int = 20): List<Book>
}