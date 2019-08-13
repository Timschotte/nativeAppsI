package com.example.myquote.persistence

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mymemory.dao.QuoteDao
import com.example.mymemory.model.Quote

/**
 * This class is used to run queries for the Quote Objects
 */
class QuoteRepository(private val quoteDao: QuoteDao){

    /**
     * Inserts a quote in the db
     */
    @WorkerThread
    fun insert(quote: Quote){
        quoteDao.insert(quote)
    }

    /**
     * Deletes a quote from the db
     */
    @WorkerThread
    fun delete(quote: Quote){
        quoteDao.delete(quote)
    }

    /**
     * Deletes all quotes from the db
     */
    @WorkerThread
    fun deleteAllQuotes(){
        quoteDao.deleteAllQuotes()
    }

    /**
     * Retrieves all quotes from the db
     */
    @WorkerThread
    fun getAllQuotes(): LiveData<List<Quote>> {
        return quoteDao.getAllQuotes()
    }

    /**
     * Retrieves the quote for a specific date
     */
    @WorkerThread
    fun getQuoteForDate(quoteDate: String): Quote {
        return quoteDao.getQuoteForDate(quoteDate)
    }
}