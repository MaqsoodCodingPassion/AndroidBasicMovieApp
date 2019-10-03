package com.noonacademy.assignment.omdb.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "media")
data class MediaEntity constructor(

        @PrimaryKey
    @SerializedName("imdbID")
    var imdbid: String,

        @SerializedName("Title")
    var title: String? = null,

        @SerializedName("Year")
    var year: String? = null,

        @SerializedName("Plot")
    var plot: String? = null,

        @SerializedName("Rated")
    var rated: String? = null,

        @SerializedName("Released")
    var released: String? = null,

        @SerializedName("Runtime")
    var runtime: String? = null,

        @SerializedName("Genre")
    var genre: String? = null,

        @SerializedName("Director")
    var director: String? = null,

        @SerializedName("Writer")
    var writer: String? = null,

        @SerializedName("Actors")
    var actors: String? = null,

        @SerializedName("Language")
    var language: String? = null,

        @SerializedName("Country")
    var contry: String? = null,

        @SerializedName("Awards")
    var awards: String? = null,

        @SerializedName("Poster")
    var poster: String? = null,

        @SerializedName("RatingsEntity")
    var mRatings: List<RatingsEntity>? = null,

        @SerializedName("Metascore")
    var metascore: String? = null,

        @SerializedName("imdbRating")
    var imdbrating: String? = null,

        @SerializedName("imdbVotes")
    var imdbvotes: String? = null,

        @SerializedName("Type")
    var type: String? = null,

        @SerializedName("DVD")
    var dvd: String? = null,

        @SerializedName("BoxOffice")
    var boxoffice: String? = null,

        @SerializedName("Production")
    var production: String? = null,

        @SerializedName("Website")
    var website: String? = null,

        @SerializedName("totalSeasons")
    var totalSeasons: String? = null,

        @SerializedName("Response")
    var response: String? = null,

        var bookmark:Boolean = false

)