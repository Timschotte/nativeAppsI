package com.example.mymemory.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "memory_table")
data class Memory(@PrimaryKey @ColumnInfo(name="id") val id: String = UUID.randomUUID().toString(),
             @ColumnInfo(name="text") val memoryText: String = "",
             @ColumnInfo(name="date") val date: String = "",
             @ColumnInfo(name="title") val title: String = "") : Serializable