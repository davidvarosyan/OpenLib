package com.test.openlib.data.model

import com.test.openlib.domain.model.Book
import kotlinx.serialization.Serializable

@Serializable
data class BookModel(
    val key: String,
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?,
    val cover_i: Int?
)

fun BookModel.toBook(subject: String): Book =
    Book(key, title, author_name, first_publish_year, cover_i, subject)

