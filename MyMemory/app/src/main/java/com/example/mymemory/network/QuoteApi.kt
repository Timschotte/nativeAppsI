package com.example.mymemory.network
import com.example.mymemory.model.QuoteResource
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
interface QuoteApi {

    /**
     * Get a QuoteData from the API
     */
    @GET("qod")
    fun getQuote(): Observable<QuoteResource.QuoteData>
}