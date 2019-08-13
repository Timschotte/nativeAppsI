package com.example.mymemory.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymemory.model.Quote
/**
 * QuoteDao
 */
@Dao
interface QuoteDao{
    /**
    * Retrieves all quotes
    */
    @Query("SELECT * from quote_table")
    fun getAllQuotes(): LiveData<List<Quote>>
    /**
     * Insert a quote
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(quote: Quote)
    /**
     * Delete a quote
     */
    @Delete
    fun delete(quote: Quote)
    /**
     * Delete all quotes
     */
    @Query("DELETE FROM quote_table")
    fun deleteAllQuotes()

    /**
     * Retrieves a quote for a certain date
     */
    @Query("SELECT * from quote_table WHERE date = :quoteDate")
    fun getQuoteForDate(quoteDate: String): Quote
}