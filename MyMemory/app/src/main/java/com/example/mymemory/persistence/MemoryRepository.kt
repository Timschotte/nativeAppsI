package com.example.mymemory.persistence

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mymemory.dao.MemoryDao
import com.example.mymemory.model.Memory

class MemoryRepository(private val memoryDao: MemoryDao){

    @WorkerThread
    fun insert(memory: Memory){
        memoryDao.insert(memory)
    }

    @WorkerThread
    fun delete(memory: Memory){
        memoryDao.delete(memory)
    }

    @WorkerThread
    fun deleteAllMemories(){
        memoryDao.deleteAllMemories()
    }

    @WorkerThread
    fun getAllMemories(): LiveData<List<Memory>>{
        return memoryDao.getAllMemories()
    }
}