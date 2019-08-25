package com.khan.movieskotlin.model

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class MoviesListResponse {

    @SerializedName("Response")
    var response: String? = null

    @SerializedName("Error")
    var error: String? = null

    @SerializedName("totalResults")
    var totalResults: String? = null

    @SerializedName("Search")
    var search: ArrayList<SearchItem>? = null
}