package com.gojek.assignment.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msk.movies.model.MediaEntity

@Dao
interface MediaDao {

    @Query("SELECT * FROM media")
    fun loadGithubRepoData(): LiveData<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieDetailsRecord(movieDetails: MediaEntity)

}
