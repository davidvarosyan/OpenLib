package com.test.openlib.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.test.openlib.domain.model.Book
import com.test.openlib.ui.common.LoadingState
import com.test.openlib.ui.viewmodel.BookSearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookSearchScreen(onBookClick: (id: String) -> Unit) {
    val viewModel: BookSearchViewModel = koinViewModel<BookSearchViewModel>()
    val loadingState by viewModel.loadingState.collectAsState()
    val books: List<Book> by viewModel.books.collectAsState()
    val subject by viewModel.subject.collectAsState()

    Column {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TextField for input
            OutlinedTextField(
                value = subject,
                onValueChange = { viewModel.onSubjectChange(it) },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.searchBooks(subject, 1)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                }
            )
        }
        when (loadingState) {
            LoadingState.EmptyState -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        "No results to show,\nsearch to see the results",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            LoadingState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn {
                    items(books) { book ->
                        BookItem(book = book, onClick = { book.key?.let { onBookClick(it) } })
                    }
                    books.apply {
                        when {
                            loadingState is LoadingState.Refreshing -> {
                                books.apply {
                                    item {
                                        Box(
                                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        if (loadingState != LoadingState.Loading) {
                            LaunchedEffect(books) {
                                viewModel.loadNextPage()
                            }
                        }
                    }
                }
            }
        }

    }

}