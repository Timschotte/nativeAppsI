package com.example.mymemory.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mymemory.App
import com.example.mymemory.base.BaseViewModel
import com.example.mymemory.model.Quote
import com.example.mymemory.model.QuoteResource
import com.example.mymemory.network.QuoteApi
import com.example.mymemory.persistence.MemoryRepository
import com.example.myquote.persistence.QuoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * This viewmodel is used to retrieve a quotemodel from a public API
 */
class QuoteViewModel : BaseViewModel() {

    /**
     * The quote retrieved from the API
     */
    private val quoteObject = MutableLiveData<Quote>()



    /**
     * The instance of the QuoteApi class
     * to get back the results of the API
     */
    @Inject
    lateinit var quoteApi : QuoteApi

    /**
     * Represents a disposable resources
     */
    private  var subscription: Disposable

    init {
        subscription = quoteApi.getQuote()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveQuoteSucces(result.contents.quotes.get(0)) },
                { error -> onRetrieveQuoteError(error) }
            )

    }

    private fun onRetrieveQuoteError(error: Throwable) {
        Log.e("quoteviewmodel", error.message)
    }

    private fun onRetrieveQuoteSucces(result: Quote) {
        quoteObject.value = result
    }


    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun getQuoteObject(): MutableLiveData<Quote> {
        return quoteObject
    }



}