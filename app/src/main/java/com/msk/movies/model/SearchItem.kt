package com.khan.movieskotlin.model

import com.google.gson.annotations.SerializedName

class SearchItem {

    @SerializedName("Type")
    var type: String? = null

    @SerializedName("Year")
    var year: String? = null

    @SerializedName("imdbID")
    var imdbID: String? = null

    @SerializedName("Poster")
    var poster: String? = null

    @SerializedName("Title")
    var title: String? = null
}