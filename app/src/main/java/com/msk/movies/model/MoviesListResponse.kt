package com.msk.movies.model

import com.google.gson.annotations.SerializedName
import com.msk.movies.model.SearchItem

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