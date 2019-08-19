package com.esi.dz_now.model

class ApiResponse {

    var posts: List<ArticleModel>? = null
    var error: Throwable? = null
    var code : Int? = null

    constructor(posts: List<ArticleModel>) {
        this.posts = posts
        this.error = null
    }

    constructor(error: Throwable) {
        this.error = error
        this.posts = null
    }

    constructor(code : Int){
        this.code = code
    }
}

