package com.example.mymemory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymemory.App
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import com.example.mymemory.persistence.MemoryRepository
import com.example.myquote.persistence.QuoteRepository
import javax.inject.Inject

/**
 * This ViewModel will provide access to the memory - and quoterepository
 */
class MemoryViewModel : ViewModel(){
    /**
     * The memoryrepository
     */
    @Inject
    lateinit var memoryRepository: MemoryRepository

    /**
     * The quoterepository
     */
    @Inject
    lateinit var quoteRepository: QuoteRepository

    /**
     * Injecting the applicationcontext
     */
    init{
        App.component.inject(this)
    }

    /**
     * Insert a memory in the repositry
     */
    fun insertMemory(memory: Memory){
        memoryRepository.insert(memory)
    }

    /**
     * Retrieve all memories from the repository
     */
    fun getAllMemories(): LiveData<List<Memory>> {
        return memoryRepository.getAllMemories()
    }

    /**
     * Delete a memory from the repository
     */
    fun deleteMemory(memory: Memory){
        return memoryRepository.delete(memory)
    }

    /**
     * Delete all memories from the repository
     */
    fun deleteAllMemories(){
        return memoryRepository.deleteAllMemories()
    }

    /**
     * Retrieve a quote for a specific date
     */
    fun getQuoteForDate(quoteDate: String): Quote? {
        return quoteRepository.getQuoteForDate(quoteDate)
    }

    /**
     * Insert a quote in the repository
     */
    fun insertQuote(quote: Quote){
        quoteRepository.insert(quote)
    }


}