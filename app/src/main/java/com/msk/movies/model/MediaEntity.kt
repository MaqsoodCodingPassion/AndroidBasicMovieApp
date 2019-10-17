package com.msk.movies.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "media")
class MediaEntity {

    @PrimaryKey
    @NonNull
    @SerializedName("imdbID")
    var imdbID: String? = null

    @SerializedName("Metascore")
    var metascore: String? = null

    @SerializedName("BoxOffice")
    var boxOffice: String? = null

    @SerializedName("Website")
    var website: String? = null

    @SerializedName("imdbRating")
    var imdbRating: String? = null

    @SerializedName("imdbVotes")
    var imdbVotes: String? = null

    @SerializedName("Ratings")
    var ratings: List<RatingsEntity>? = null

    @SerializedName("Runtime")
    var runtime: String? = null

    @SerializedName("Language")
    var language: String? = null

    @SerializedName("Rated")
    var rated: String? = null

    @SerializedName("Production")
    var production: String? = null

    @SerializedName("Released")
    var released: String? = null

    @SerializedName("Plot")
    var plot: String? = null

    @SerializedName("Director")
    var director: String? = null

    @SerializedName("Title")
    var title: String? = null

    @SerializedName("Actors")
    var actors: String? = null

    @SerializedName("Response")
    var response: String? = null

    @SerializedName("Type")
    var type: String? = null

    @SerializedName("Awards")
    var awards: String? = null

    @SerializedName("DVD")
    var dvd: String? = null

    @SerializedName("Year")
    var year: String? = null

    @SerializedName("Poster")
    var poster: String? = null

    @SerializedName("Country")
    var country: String? = null

    @SerializedName("Genre")
    var genre: String? = null

    @SerializedName("Writer")
    var writer: String? = null
}

data class RatingsEntity constructor(

    @SerializedName("Source")
    var source: String? = null,

    @SerializedName("Value")
    var value: String? = null
)