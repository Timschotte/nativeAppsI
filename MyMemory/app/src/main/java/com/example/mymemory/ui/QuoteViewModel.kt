package com.example.mymemory.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mymemory.base.BaseViewModel
import com.example.mymemory.model.Quote
import com.example.mymemory.model.QuoteResource
import com.example.mymemory.network.QuoteApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QuoteViewModel : BaseViewModel() {


    private val quoteText = MutableLiveData<String>()



    /**
     * The instance of the QuoteApi class
     * to get back the results of the API
     */
    @Inject
    lateinit var quoteApi : QuoteApi

    /**
     * Indicates whether the loading view should be displayed.
     */
    //val loadingVisibility: MutableLiveData<Int> = MutableLiveData()


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
            .doOnSubscribe{ onRetrieveQuoteStart()}
            .doOnTerminate { onRetrieveQuoteFinish()}
            .subscribe(
                { result -> onRetrieveQuoteSucces(result.contents.quotes.get(0)) },
                { error -> onRetrieveQuoteError(error) }
            )

    }

    private fun onRetrieveQuoteError(error: Throwable) {
        Log.e("quoteviewmodel", "error")
        //Logger.e(error.message!!)
    }

    private fun onRetrieveQuoteSucces(result: Quote) {
        quoteText.value = result.quote

    }

    private fun onRetrieveQuoteFinish() {
        //TODO spinnerlike window
        //loadingVisibility.value = View.GONE
    }

    private fun onRetrieveQuoteStart() {
        //loadingVisibility.value = View.VISIBLE
    }

    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


    fun getQuoteText(): MutableLiveData<String> {
        return quoteText
    }




}