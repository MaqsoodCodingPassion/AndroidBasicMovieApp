package com.msk.movies.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.msk.movies.model.SearchItem

@Dao
interface MediaDao {

    @Query("SELECT * FROM media")
    fun loadAllMedia(): LiveData<List<SearchItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(SearchItemList: List<SearchItem>?)

    @Query("select * from media where imdbid = :mediaId")
    fun loadMedia(mediaId: String): SearchItem

    @Query("select * from media where bookmark = :isBookmarked")
    fun loadBookMarkedMedia(isBookmarked:Boolean): LiveData<List<SearchItem>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedia(SearchItem: SearchItem)

    @Query("UPDATE media SET bookmark=:bookmarked WHERE imdbid = :mediaId")
    fun update(bookmarked: Boolean, mediaId: String)

    @Query("DELETE FROM media WHERE imdbid = :mediaId")
    fun deleteMovie(mediaId: String)

    @Query("select * from media where title like '%' || :search || '%'")
    fun loadMediaFromSearch(search: String):DataSource.Factory<Int, SearchItem>
}
