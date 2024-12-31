package org.example.project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsModel(
    val title: String?,
    val description: DescriptionModel?,
    val authors: List<AuthorRefModel>?,
    val covers: List<Int>?
)