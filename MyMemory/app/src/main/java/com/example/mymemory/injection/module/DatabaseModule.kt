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

@Module
class DatabaseModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideMemoryRepository(memoryDao: MemoryDao): MemoryRepository {
        return MemoryRepository(memoryDao)
    }

    @Provides
    @Singleton
    internal fun provideQuoteRepository(quoteDao: QuoteDao): QuoteRepository {
        return QuoteRepository(quoteDao)
    }

    @Provides
    @Singleton
    internal fun provideMemoryDao(memoryDatabase: MemoryDatabase): MemoryDao {
        return memoryDatabase.memoryDao()
    }

    @Provides
    @Singleton
    internal fun provideQuotesDao(quoteDatabase: MemoryDatabase): QuoteDao {
        return quoteDatabase.quoteDao()
    }

    @Provides
    @Singleton
    internal fun provideMemoryDatabase(context: Context): MemoryDatabase {
        return MemoryDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

}