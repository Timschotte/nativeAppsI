package com.example.mymemory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymemory.App
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import com.example.mymemory.persistence.MemoryRepository
import com.example.myquote.persistence.QuoteRepository
import javax.inject.Inject

class MemoryViewModel : ViewModel(){
    @Inject
    lateinit var memoryRepository: MemoryRepository

    @Inject
    lateinit var quoteRepository: QuoteRepository

    init{
        App.component.inject(this)
    }

    fun insertMemory(memory: Memory){
        memoryRepository.insert(memory)
    }

    fun getAllMemories(): LiveData<List<Memory>> {
        return memoryRepository.getAllMemories()
    }

    fun deleteMemory(memory: Memory){
        return memoryRepository.delete(memory)
    }

    fun deleteAllMemories(){
        return memoryRepository.deleteAllMemories()
    }

    fun getQuoteForDate(quoteDate: String): Quote? {
        return quoteRepository.getQuoteForDate(quoteDate)
    }

    fun insertQuote(quote: Quote){
        quoteRepository.insert(quote)
    }


}