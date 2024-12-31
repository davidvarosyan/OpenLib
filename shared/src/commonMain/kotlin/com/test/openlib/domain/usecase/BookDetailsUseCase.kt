package com.test.openlib.domain.usecase

import com.test.openlib.domain.core.Result
import com.test.openlib.domain.core.runSafe
import com.test.openlib.domain.model.BookDetails
import com.test.openlib.domain.repository.BookDetailsRepository

interface BookDetailsUseCase {
    suspend fun getBookDetails(): Result<BookDetails>
}

class BookDetailsUseCaseImpl(val repo: BookDetailsRepository) : BookDetailsUseCase {
    override suspend fun getBookDetails(): Result<BookDetails> = runSafe {
        repo.getBookDetails()
    }
}
