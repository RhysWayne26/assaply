package com.example.assaply.data.local

import androidx.room.TypeConverter
import com.example.assaply.data.domain.model.Source

class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id}:${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        val parts = source.split(":")
        return if (parts.size == 2) {
            Source(id = parts[0], name = parts[1])
        } else {
            Source(id = "", name = "")
        }
    }
}