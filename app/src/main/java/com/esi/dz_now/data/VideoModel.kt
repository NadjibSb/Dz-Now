package com.esi.dz_now.data

import com.google.gson.annotations.SerializedName

data class VideoModel(
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("img")
    val img: String

)