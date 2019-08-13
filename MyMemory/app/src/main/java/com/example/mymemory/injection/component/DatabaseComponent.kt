package com.example.mymemory.injection.component

import com.example.mymemory.App
import com.example.mymemory.injection.module.DatabaseModule
import com.example.mymemory.injection.module.NetworkModule
import com.example.mymemory.ui.MemoryViewModel
import com.example.mymemory.ui.QuoteViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * This will inject the databasecomponent into our MemoryViewModel
 */
@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun inject(memoryViewModel: MemoryViewModel)
    fun inject(app: App)

}