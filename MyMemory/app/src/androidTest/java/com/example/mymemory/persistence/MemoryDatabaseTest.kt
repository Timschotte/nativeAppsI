package com.example.mymemory.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mymemory.TestUtils.getValue
import com.example.mymemory.dao.MemoryDao
import com.example.mymemory.dao.QuoteDao
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MemoryDatabaseTest {

    private lateinit var memoryDao: MemoryDao
    private lateinit var quoteDao: QuoteDao

    private lateinit var db: MemoryDatabase

    private val quote1 = Quote("Our words are buttressed by our deeds, and our deeds are inspired by our convictions.",
    85,
    "Theodore Hesburgh",
    listOf("conviction"),
    "inspire",
    "2019-08-03",
    "Inspiring quote of the day",
    "abOiOZ1mvAntbk4JjR2lvweF")

    private val quote2 = Quote("Do not worry if you have built your castles in the air. They are where they should be. Now put the foundations under them.",
        122,
        "Henry David Thoreau",
        listOf("dreams"),
        "inspire",
        "2016-11-21",
        "Inspiring quote of the day",
        "mYpH8syTM8rf8KFORoAJmQeF")

    private val memory1 = Memory("0", "Today I worked for school", "2016-11-21","Working on the future")
    private val memory2 = Memory("1", "Today I chilled out", "2019-08-03", "Relaxing times")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context, MemoryDatabase::class.java).allowMainThreadQueries().build()
        quoteDao = db.quoteDao()
        memoryDao = db.memoryDao()


        memoryDao.insert(memory1)
        memoryDao.insert(memory2)

        quoteDao.insert(quote1)
        quoteDao.insert(quote2)
    }

    @Test
    fun insertMemories_returnAllMemories() {
        val memories = getValue(memoryDao.getAllMemories())
        assertEquals(2, memories.size)
    }

    @Test
    fun insertQuotes_returnsAllQuotes() {
        val quotes = getValue(quoteDao.getAllQuotes())
        assertEquals(2, quotes.size)
    }

    @Test
    fun deleteAllMemories_deletsAllMemories(){
        memoryDao.deleteAllMemories()
        val memories = getValue(memoryDao.getAllMemories())
        assertEquals(0, memories.size)
    }


    @After
    fun breakDown(){
        db.close()
    }


}
