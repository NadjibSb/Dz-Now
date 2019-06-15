package com.esi.dz_now.data

import java.util.*

data class Article(
    val id: Int,
    val title: String,
    val img: Int,
    val content: String,
    val categories: Categories,
    val date: Date,
    var favorit: Boolean,
    val source: String,
    val author: String
) {
}