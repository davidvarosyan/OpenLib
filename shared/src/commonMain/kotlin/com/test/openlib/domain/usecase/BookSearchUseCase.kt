package com.test.openlib.domain.usecase

import com.test.openlib.domain.core.Result
import com.test.openlib.domain.core.runSafe
import com.test.openlib.domain.model.Book
import com.test.openlib.domain.repository.BookSearchRepository

interface BookSearchUseCase {
    suspend fun getBooks(subject: String, page: Int): Result<List<Book>>
}

class BookSearchUseCaseImpl(val bookSearchRepository: BookSearchRepository) : BookSearchUseCase {
    override suspend fun getBooks(subject: String, page: Int): Result<List<Book>> = runSafe {
        bookSearchRepository.getBooks(subject, page)
    }
}