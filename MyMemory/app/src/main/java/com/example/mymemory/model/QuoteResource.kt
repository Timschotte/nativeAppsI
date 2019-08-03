package com.example.mymemory.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class QuoteResource {
    @Parcelize
    data class Quote(
        @field:Json(name = "quote") val quote: String,
        @field:Json(name = "length") val length: Int,
        @field:Json(name = "author") val author: String,
        @field:Json(name = "tags") val tags: List<String>,
        @field:Json(name = "category") val category: String,
        @field:Json(name = "date") val date: String,
        @field:Json(name = "title") val title: String,
        @field:Json(name = "id") val id: String
    ) : Parcelable, Serializable

    @Parcelize
    data class Success(
        @field:Json(name = "total") val total: Int
    ) : Parcelable

    @Parcelize
    data class Contents(
        @field:Json(name = "quotes") val quotes: List<Quote>
    ) : Parcelable

    @Parcelize
    data class QuoteData(
        @field:Json(name = "success") val success: Success,
        @field:Json(name = "contents") val contents: Contents
    ) : Parcelable

}