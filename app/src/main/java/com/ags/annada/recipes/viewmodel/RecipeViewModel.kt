package com.ags.annada.recipes.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ags.annada.recipes.room.entities.Recipe

class RecipeViewModel : BaseViewModel() {
    val id = MutableLiveData<Long>()
    private val title = MutableLiveData<String>()
    private val imageURL = MutableLiveData<String>()
    private val ingredients = MutableLiveData<String>()
    private val minutes = MutableLiveData<String>()

    fun bind(recipeObj: Recipe) {
        id.value = recipeObj.id
        title.value = recipeObj.name
        imageURL.value = recipeObj.imageURL
        ingredients.value = recipeObj.ingredients?.size.toString() + " Ingredients"
        minutes.value = recipeObj.timers?.size.toString()  + " Minutes"
    }

    fun getTitle(): MutableLiveData<String> {
        return title
    }

    fun getImageUrl(): MutableLiveData<String> {
        return imageURL
    }

    fun getIngredients(): MutableLiveData<String> {
        return ingredients
    }

    fun getMinutes(): MutableLiveData<String> {
        return minutes
    }
}