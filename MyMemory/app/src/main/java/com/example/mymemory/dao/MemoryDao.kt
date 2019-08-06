package com.example.mymemory.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mymemory.model.Memory

@Dao
interface MemoryDao{
    @Query("SELECT * from memory_table")
    fun getAllMemories(): LiveData<List<Memory>>

    @Insert
    fun insert(memory: Memory)

    @Delete
    fun delete(memory: Memory)

    @Query("DELETE FROM memory_table")
    fun deleteAllMemories()
}