package com.msk.movies.model
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingsConverter {

    private val mGson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<RatingsEntity> {
        if (data.isNullOrEmpty() || data.equals("null")) {
            return emptyList()
        }

        return mGson.fromJson(data, object : TypeToken<List<RatingsEntity>>() {}.type)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<RatingsEntity>?): String {
        return mGson.toJson(someObjects)
    }
}