package com.example.mymemory.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote

@Dao
interface QuoteDao{
    @Query("SELECT * from quote_table")
    fun getAllQuotes(): LiveData<List<Quote>>

    @Insert
    fun insert(quote: Quote)

    @Delete
    fun delete(quote: Quote)

    @Query("DELETE FROM quote_table")
    fun deleteAllQuotes()
}