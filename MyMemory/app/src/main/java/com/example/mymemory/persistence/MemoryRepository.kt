package com.example.mymemory.persistence

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mymemory.dao.MemoryDao
import com.example.mymemory.model.Memory

/**
 * This class is used to run queries for the Memory Objects
 */
class MemoryRepository(private val memoryDao: MemoryDao){

    /**
     * Inserts a memory in the db
     */
    @WorkerThread
    fun insert(memory: Memory){
        memoryDao.insert(memory)
    }

    /**
     * Deletes a memory from the db
     */
    @WorkerThread
    fun delete(memory: Memory){
        memoryDao.delete(memory)
    }

    /**
     * Deletes all memories from the db
     */
    @WorkerThread
    fun deleteAllMemories(){
        memoryDao.deleteAllMemories()
    }

    /**
     * Retrieves all memories from the db
     */
    @WorkerThread
    fun getAllMemories(): LiveData<List<Memory>>{
        return memoryDao.getAllMemories()
    }
}