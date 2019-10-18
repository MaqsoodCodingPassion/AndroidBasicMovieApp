package com.gojek.assignment.db


import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msk.movies.model.MediaEntity

@Dao
interface MediaDao {

    @Query("SELECT * FROM media")
    fun loadMovieListRecords(): LiveData<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieDetailsRecord(movieDetails: MediaEntity)

    @Query("select * from media where imdbid = :mediaId")
    fun loadMedia(mediaId: String): LiveData<MediaEntity>

    @Query("UPDATE media SET bookmark=:bookmarked WHERE imdbid = :mediaId")
    fun updateMovieWithBookMark(bookmarked: Boolean, mediaId: String)

    @Query("select * from media where bookmark = :isBookmarked")
    fun loadBookMarkedList(isBookmarked:Boolean): LiveData<List<MediaEntity>>

    @Query("DELETE FROM media WHERE imdbid = :mediaId")
    fun deleteMovie(mediaId: String)

    @Query("select * from media where title like '%' || :search || '%'")
    fun loadMediaFromSearch(search: String): DataSource.Factory<Int, MediaEntity>
}
