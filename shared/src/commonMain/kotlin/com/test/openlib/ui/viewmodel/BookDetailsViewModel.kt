package com.test.openlib.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.test.openlib.domain.usecase.BookSearchUseCase

class BookDetailsViewModel(val bookDetailUseCase: BookSearchUseCase) : ViewModel() {
}