package com.example.mymemory.persistence

import androidx.room.TypeConverter

/**
 * Converts a List of strings to a comma-seperated-String & reverse
 */
class Converters {
    @TypeConverter
    fun toString(tags: List<String> ): String {
        return tags.joinToString()
    }

    @TypeConverter
    fun toTags(tagsString: String): List<String> {
        return tagsString.split(",").map{it.trim()}
    }
}