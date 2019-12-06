package com.ags.annada.recipes.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ags.annada.recipes.room.RecipeDatabase

class RecipeDetailViewModelFactory(private val activity: AppCompatActivity, private val recipeId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                RecipeDatabase::class.java,
                "recipe"
            ).build()
            @Suppress("UNCHECKED_CAST")
            return RecipeDetailViewModel(db.recipeDao(), recipeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}