package com.example.mymemory.injection.component

import com.example.mymemory.App
import com.example.mymemory.injection.module.DatabaseModule
import com.example.mymemory.ui.MemoryViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun inject(memoryViewModel: MemoryViewModel)
    fun inject(app: App)

}