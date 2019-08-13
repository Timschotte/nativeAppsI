package com.example.mymemory.injection.module

import android.app.Application
import android.content.Context
import com.example.mymemory.dao.MemoryDao
import com.example.mymemory.dao.QuoteDao
import com.example.mymemory.persistence.MemoryDatabase
import com.example.mymemory.persistence.MemoryRepository
import com.example.myquote.persistence.QuoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides the Database Service implemenation
 * @param application the applicationContext used to instantiate the service
 */
@Module
class DatabaseModule(private val application: Application) {

    /**
     * Shows how to create a MemoryRepository
     *  @param memoryDao the MemoryDao used to instantiate the Repository
     */
    @Provides
    @Singleton
    internal fun provideMemoryRepository(memoryDao: MemoryDao): MemoryRepository {
        return MemoryRepository(memoryDao)
    }

    /**
     * Shows how to create a QuoteRepository
     *  @param quoteDao the QuoteDao used to instantiate the Repository
     */
    @Provides
    @Singleton
    internal fun provideQuoteRepository(quoteDao: QuoteDao): QuoteRepository {
        return QuoteRepository(quoteDao)
    }

    /**
     * Shows how to create a MemoryDao
     *  @param memoryDatabase the MemoryDatabase used to instantiate the Dao
     */
    @Provides
    @Singleton
    internal fun provideMemoryDao(memoryDatabase: MemoryDatabase): MemoryDao {
        return memoryDatabase.memoryDao()
    }

    /**
     * Shows how to create a QuoteDao
     *  @param quoteDatabase the MemoryDatabase used to instantiate the Dao
     */
    @Provides
    @Singleton
    internal fun provideQuotesDao(quoteDatabase: MemoryDatabase): QuoteDao {
        return quoteDatabase.quoteDao()
    }

    /**
     * Shows how to create a MemoryDatabase
     *  @param context the Context used to instantiate the Database
     */
    @Provides
    @Singleton
    internal fun provideMemoryDatabase(context: Context): MemoryDatabase {
        return MemoryDatabase.getDatabase(context)
    }

    /**
     * Shows how to create an ApplicationContext
     */
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

}