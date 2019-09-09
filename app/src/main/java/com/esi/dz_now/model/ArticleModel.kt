package com.esi.dz_now.model


data class ArticleModel(

    var id: String = "",
    var title: String = "",
    var url: String = "",
    var content: String = "",
    var img: String,
    var category: String = "",
    var date: String = "",
    var source: String = "",
    var favoris: Boolean = false

)