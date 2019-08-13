package com.example.mymemory.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymemory.model.Memory

/**
 * MemoryDao
 */
@Dao
interface MemoryDao{
    /**
     * Retrieves all memories
     */
    @Query("SELECT * from memory_table")
    fun getAllMemories(): LiveData<List<Memory>>

    /**
     * Insert a memory
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memory: Memory)

    /**
     * Delete a memory
     */
    @Delete
    fun delete(memory: Memory)

    /**
     * Delete all memories
     */
    @Query("DELETE FROM memory_table")
    fun deleteAllMemories()
}