package com.ags.annada.recipes.dagger.component

import com.ags.annada.recipes.dagger.module.NetworkModule
import com.ags.annada.recipes.viewmodel.RecipeListViewModel
import com.ags.annada.recipes.viewmodel.RecipeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(recipeListViewModel: RecipeListViewModel)

    fun inject(recipeViewModel: RecipeViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}