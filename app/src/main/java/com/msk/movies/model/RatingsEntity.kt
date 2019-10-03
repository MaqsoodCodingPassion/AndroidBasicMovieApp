package com.noonacademy.assignment.omdb.movies.model
import com.google.gson.annotations.SerializedName

data class RatingsEntity constructor(

    @SerializedName("Source")
    var source: String? = null,

    @SerializedName("Value")
    var value: String? = null
)
