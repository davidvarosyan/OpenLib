package com.test.openlib.data.repository

import androidx.compose.ui.util.fastForEach
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.test.openlib.data.model.toBook
import com.test.openlib.db.BookDatabase
import com.test.openlib.domain.model.Book
import com.test.openlib.domain.repository.BookSearchRepository
import com.test.openlib.domain.repository.NetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.model.SearchResultModel

class BookSearchRepositoryImpl(
    private val apiService: HttpClient,
    private val bookDatabase: BookDatabase,
    private val networkRepository: NetworkRepository,
    private val dataStore: DataStore<Preferences>
) :
    BookSearchRepository {
    override suspend fun getBooks(subject: String, page: Int, offset: Int): List<Book> {
        if (networkRepository.isNetworkAvailable()) {
            val response: SearchResultModel =
                apiService.get("https://openlibrary.org/search.json?subject=$subject&limit=10&page=$page")
                    .body()
            val result = response.docs.map { it.toBook(subject) }.apply {
                fastForEach {
                    bookDatabase.bookDatabaseQueries.insertBook(
                        it.key,
                        it.title.toString(),
                        it.author_name.toString(),
                        it.cover_i.toString(),
                        it.first_publish_year.toString(),
                        subject
                    )
                }
            }

            return result
        } else {
            return bookDatabase.bookDatabaseQueries.selectBooksBySubjectWithLimit(
                subject,
                offset.toLong(),
                (page - 1) * offset.toLong()
            ).executeAsList().map {
                Book(
                    key = it.id,
                    title = it.title,
                    author_name = it.author?.removeSurrounding("[", "]")?.split("."),
                    first_publish_year = it.published_date?.toInt(),
                    cover_i = it.cover_url?.toInt(),
                    subject = it.subjects
                )
            }
        }
    }
}