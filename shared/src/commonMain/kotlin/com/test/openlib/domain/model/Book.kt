package com.test.openlib.domain.model

data class Book(
    val key: String,
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?,
    val cover_i: Int?,
    val subject: String?
)