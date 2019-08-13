package com.example.mymemory.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * Model for a Memory Object
 * @param id the Id
 * @param memoryText the text
 * @param date the date the Memory was created
 * @param title the title
 */
@Entity(tableName = "memory_table")
data class Memory(@PrimaryKey @ColumnInfo(name="id") val id: String = UUID.randomUUID().toString(),
             @ColumnInfo(name="text") val memoryText: String = "",
             @ColumnInfo(name="date") val date: String = "",
             @ColumnInfo(name="title") val title: String = "") : Serializable