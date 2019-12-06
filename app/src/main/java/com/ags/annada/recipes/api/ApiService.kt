package com.ags.annada.recipes.api

import com.ags.annada.recipes.room.entities.Recipe
import retrofit2.http.GET

interface ApiService {
    @GET("recipes.json")
    suspend fun getRecipes(): List<Recipe>
}