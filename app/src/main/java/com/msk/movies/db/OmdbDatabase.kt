/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.msk.movies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msk.movies.model.RatingsConverter
import com.msk.movies.model.SearchItem

/**
 * Database schema that holds the list of repos.
 */
@Database(
        entities = [SearchItem::class],
        version = 1,
        exportSchema = false
)

@TypeConverters(RatingsConverter::class)
abstract class OmdbDatabase : RoomDatabase() {

    abstract fun mediaDao(): MediaDao

    companion object {

        @Volatile
        private var INSTANCE: OmdbDatabase? = null

        fun getInstance(context: Context): OmdbDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        OmdbDatabase::class.java, "Github.db")
                        .build()
    }
}
