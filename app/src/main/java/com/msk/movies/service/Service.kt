package com.msk.movies.service

import com.msk.movies.model.MediaEntity
import com.msk.movies.model.MoviesListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    //"http://www.omdbapi.com/?i=tt0063300&plot=full&apikey=7294277f"

    @GET("/")
    fun getMoviesList(
        @Query("s") movieName: String, @Query("apikey") key: String,
        @Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<MoviesListResponse>

    @GET("/")
    fun getMovieDetails(
        @Query("i") id: String, @Query("plot") plot: String, @Query("apikey") key: String): Single<MediaEntity>
}
