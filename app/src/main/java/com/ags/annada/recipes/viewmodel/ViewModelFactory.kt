package com.ags.annada.recipes.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ags.annada.recipes.room.RecipeDatabase

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                RecipeDatabase::class.java,
                "recipe"
            ).build()
            @Suppress("UNCHECKED_CAST")
            return RecipeListViewModel(db.recipeDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}