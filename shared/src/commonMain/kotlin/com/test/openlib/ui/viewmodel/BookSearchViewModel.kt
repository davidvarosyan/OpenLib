package com.test.openlib.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.openlib.domain.core.Result
import com.test.openlib.domain.model.Book
import com.test.openlib.domain.model.SearchAction
import com.test.openlib.domain.usecase.BookSearchUseCase
import com.test.openlib.domain.usecase.SearchActionUseCase
import com.test.openlib.ui.common.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookSearchViewModel(
    private val bookSearchUseCase: BookSearchUseCase,
    private val searchActionUseCase: SearchActionUseCase
) : ViewModel() {
    private var currentSubject = ""
    private var currentPage = 1
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.EmptyState)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    private val _searchActionState = MutableStateFlow<SearchAction?>(null)
    val searchActionState: StateFlow<SearchAction?> = _searchActionState.asStateFlow()

    private val _subjectState = MutableStateFlow<String>("")
    val subject: StateFlow<String> = _subjectState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.emit(LoadingState.Loading)
            _searchActionState.emit(searchActionUseCase.getLastAction())
            if (_searchActionState.value == null) {
                _loadingState.emit(LoadingState.EmptyState)
            } else {
                _loadingState.emit(LoadingState.NoLoading)
            }
        }
    }

    fun onSubjectChange(subject: String) {
        _subjectState.value = subject
    }

    fun searchBooks(subject: String, page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            searchActionUseCase.getLastAction()
        }

        var appending = false
        if (books.value.isEmpty() || subject != currentSubject) {
            _loadingState.value = LoadingState.Loading
        } else if (subject == currentSubject) {
            appending = true
            _loadingState.value = LoadingState.Refreshing
        }
        currentPage = page
        currentSubject = subject
        viewModelScope.launch(Dispatchers.IO) {
            bookSearchUseCase.getBooks(subject, page).let {
                when (it) {
                    is Result.Error -> {}
                    is Result.Loading -> {}
                    is Result.Success -> {
                        if (appending) {
                            _books.value += it.data
                        } else {
                            _books.value = it.data
                        }
                    }
                }
            }

            _loadingState.value = LoadingState.NoLoading
        }
    }


    fun loadNextPage() {
        searchBooks(currentSubject, currentPage + 1)
    }

}