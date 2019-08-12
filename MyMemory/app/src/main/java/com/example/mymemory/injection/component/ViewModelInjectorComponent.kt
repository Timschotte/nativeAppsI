package com.example.mymemory.injection.component

import com.example.mymemory.injection.module.DatabaseModule
import com.example.mymemory.injection.module.NetworkModule
import com.example.mymemory.ui.QuoteViewModel
import com.example.myquote.persistence.QuoteRepository
import dagger.Component
import javax.inject.Singleton


/**
 * Component providing the inject functions for presenters.
 */

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjectorComponent {


    /**
     * Injects the dependencies into the specified QuoteViewModel.
     * @param quoteViewModel the [QuoteViewModel] in which to inject the dependencies.
     */
    fun inject(quoteViewModel: QuoteViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder

    }

}
