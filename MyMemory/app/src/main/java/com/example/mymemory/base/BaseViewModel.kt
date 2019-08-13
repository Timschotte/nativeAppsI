package com.example.mymemory.base
import androidx.lifecycle.ViewModel
import com.example.mymemory.injection.component.DaggerViewModelInjectorComponent
import com.example.mymemory.injection.component.ViewModelInjectorComponent
import com.example.mymemory.injection.module.DatabaseModule
import com.example.mymemory.injection.module.NetworkModule
import com.example.mymemory.ui.QuoteViewModel
/**
 * A wrapper for our viewmodels that injects the networkdependency
 */
abstract class BaseViewModel : ViewModel() {

    private val injectorComponent: ViewModelInjectorComponent = DaggerViewModelInjectorComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is QuoteViewModel -> injectorComponent.inject(this)
        }
    }

}