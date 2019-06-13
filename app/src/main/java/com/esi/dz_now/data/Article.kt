package com.esi.dz_now.data

import java.util.*

data class Article(
    val id:Int,
    val title:String,
    val img:String,
    val content:String,
    val categories: Categories,
    val date: Date,
    var favorit:Boolean
) {
}