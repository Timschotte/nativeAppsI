package com.example.myquote.persistence

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mymemory.dao.QuoteDao
import com.example.mymemory.model.Quote


class QuoteRepository(private val quoteDao: QuoteDao){

    @WorkerThread
    fun insert(quote: Quote){
        quoteDao.insert(quote)
    }

    @WorkerThread
    fun delete(quote: Quote){
        quoteDao.delete(quote)
    }

    @WorkerThread
    fun deleteAllQuotes(){
        quoteDao.deleteAllQuotes()
    }

    @WorkerThread
    fun getAllQuotes(): LiveData<List<Quote>> {
        return quoteDao.getAllQuotes()
    }

    @WorkerThread
    fun getQuoteForDate(quoteDate: String): Quote {
        return quoteDao.getQuoteForDate(quoteDate)
    }
}