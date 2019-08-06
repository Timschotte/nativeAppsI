package com.example.mymemory.persistence

import android.content.Context
import android.os.AsyncTask
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mymemory.R
import com.example.mymemory.dao.MemoryDao
import com.example.mymemory.dao.QuoteDao
import com.example.mymemory.model.Memory
import com.example.mymemory.model.Quote
import okhttp3.internal.Internal.instance

/**
 * The Room database that contains the Memories table
 */
@Database(entities = arrayOf(Memory::class, Quote::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MemoryDatabase : RoomDatabase() {
    abstract fun memoryDao(): MemoryDao
    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var INSTANCE: MemoryDatabase? = null

        fun getDatabase(context: Context): MemoryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemoryDatabase::class.java,
                    "Memory_database"
                ).addCallback(roomCallback)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        //we create a callback for sampledata
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(INSTANCE)
                    .execute()
            }
        }
    }

    //We create the sampledata and add it to the DB
    class PopulateDbAsyncTask(db: MemoryDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val memoryDao = db?.memoryDao()
        private val quoteDao = db?.quoteDao()

        override fun doInBackground(vararg p0: Unit?) {
            memoryDao?.insert(Memory("0", "Today I worked for school", "2016-11-21","Working on the future"))
            memoryDao?.insert(Memory("1", "Today I chilled out", "2019-08-03", "Relaxing times"))

            quoteDao?.insert(Quote("Do not worry if you have built your castles in the air. They are where they should be. Now put the foundations under them.",
                122,
                "Henry David Thoreau",
                listOf("dreams"),
                "inspire",
                "2016-11-21",
                "Inspiring quote of the day",
                "mYpH8syTM8rf8KFORoAJmQeF"))
            quoteDao?.insert(Quote("Our words are buttressed by our deeds, and our deeds are inspired by our convictions.",
                85,
                "Theodore Hesburgh",
                listOf("conviction"),
                "inspire",
                "2019-08-03",
                "Inspiring quote of the day",
                "abOiOZ1mvAntbk4JjR2lvweF"))

        }
    }
}