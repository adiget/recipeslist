package com.ags.annada.recipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.ags.annada.recipes.room.daos.RecipeDao
import com.ags.annada.recipes.room.entities.Recipe
import com.ags.annada.recipes.view.IngredientsListAdapter
import com.ags.annada.recipes.view.StepListAdapter
import kotlinx.coroutines.Job

class RecipeDetailViewModel(private val recipeDao: RecipeDao, private val recipeId: Long) :
    BaseViewModel() {
    private val viewModelJob = Job()
    val ingredientsListAdapter: IngredientsListAdapter = IngredientsListAdapter()
    val stepsAdapter: StepListAdapter = StepListAdapter()


    private val recipe: LiveData<Recipe> = recipeDao.getRecipeWithId(recipeId)
    val title = map(recipe) { it.name }
    val imageURL = map(recipe) { it.imageURL }
    val ingredientsLabel = map(recipe){"Ingredients : "+ it.ingredients?.size}
    val ingredientsList = map(recipe) { it.ingredients}
    val instructions = map(recipe){it.steps}


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}