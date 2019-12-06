package com.ags.annada.recipes.viewmodel

import androidx.lifecycle.ViewModel
import com.ags.annada.recipes.dagger.component.DaggerViewModelInjector
import com.ags.annada.recipes.dagger.component.ViewModelInjector
import com.ags.annada.recipes.dagger.module.NetworkModule

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
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
            is RecipeListViewModel -> injector.inject(this)
            is RecipeViewModel -> injector.inject(this)
        }
    }
}