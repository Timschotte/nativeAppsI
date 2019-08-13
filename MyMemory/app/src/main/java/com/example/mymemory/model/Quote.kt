package com.example.mymemory.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mymemory.persistence.Converters
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Model for a Quote Object
 * @param quote the text of the quote
 * @param length the length of the quote
 * @param author the author
 * @param tags the tags
 * @param category the category
 * @param date the date this quote was quote of the day
 * @param title the title
 * @param id the id
 */
@Parcelize
@Entity(tableName = "quote_table")
data class Quote(
    @field:Json(name = "quote") val quote: String = "",
    @field:Json(name = "length") val length: Int = 0,
    @field:Json(name = "author") val author: String = "",
    @TypeConverters(Converters::class) @field:Json(name = "tags") val tags: List<String> = listOf<String>(),
    @field:Json(name = "category") val category: String = "",
    @PrimaryKey @field:Json(name = "date") val date: String = "",
    @field:Json(name = "title") val title: String = "",
    @field:Json(name = "id") val id: String = ""
) : Parcelable, Serializable