package ar.edu.unicen.seminariomobile.data

import androidx.room.TypeConverter
import ar.edu.unicen.seminariomobile.data.dto.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromGenreList(value: List<Genre>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGenreList(value: String?): List<Genre>? {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, listType)
    }
}