package com.example.mymemory.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymemory.App
import com.example.mymemory.model.Memory
import com.example.mymemory.persistence.MemoryRepository
import com.example.myquote.persistence.QuoteRepository
import javax.inject.Inject

class MemoryViewModel : ViewModel(){
    @Inject
    lateinit var memoryRepository: MemoryRepository

    //private var allMemories: LiveData<List<Memory>> = memoryRepository.getAllMemories()


    init{
        App.component.inject(this)
    }

    fun insert(memory: Memory){
        memoryRepository.insert(memory)
    }

    fun getAllMemories(): LiveData<List<Memory>> {
        return memoryRepository.getAllMemories()
    }


}