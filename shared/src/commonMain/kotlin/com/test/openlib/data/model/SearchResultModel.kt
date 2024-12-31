package org.example.project.data.model

import com.test.openlib.data.model.BookModel
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultModel(
    val docs: List<BookModel>
)