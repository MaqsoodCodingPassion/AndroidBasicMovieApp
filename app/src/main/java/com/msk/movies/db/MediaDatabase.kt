package com.msk.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msk.movies.model.MediaEntity
import com.msk.movies.model.RatingsConverter

@TypeConverters(RatingsConverter::class)
@Database(entities = [MediaEntity::class], version = 1,exportSchema = false)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}