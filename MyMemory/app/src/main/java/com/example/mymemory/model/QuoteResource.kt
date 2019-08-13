package com.example.mymemory.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Model for a QuoteResource - The model we receive from the public API
 * @param Success
 * @param Contents a list of quotes
 * @param QuoteData
 */
class QuoteResource {
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