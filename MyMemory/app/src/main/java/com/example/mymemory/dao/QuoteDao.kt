package com.example.mymemory.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymemory.model.Quote

@Dao
interface QuoteDao{
    @Query("SELECT * from quote_table")
    fun getAllQuotes(): LiveData<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(quote: Quote)

    @Delete
    fun delete(quote: Quote)

    @Query("DELETE FROM quote_table")
    fun deleteAllQuotes()

    @Query("SELECT * from quote_table WHERE date = :quoteDate")
    fun getQuoteForDate(quoteDate: String): Quote
}