package com.example.mymemory

import android.app.Application
import com.example.mymemory.injection.component.DaggerDatabaseComponent
import com.example.mymemory.injection.component.DatabaseComponent
import com.example.mymemory.injection.module.DatabaseModule
import com.example.mymemory.injection.module.NetworkModule
import javax.inject.Inject

/**
 * This is the applicationContext used in the application
 */
class App : Application() {
    companion object {
        lateinit var component: DatabaseComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDatabaseComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}
