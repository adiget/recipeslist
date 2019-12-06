package com.ags.annada.recipes.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ags.annada.recipes.room.entities.Recipe
import io.reactivex.Observable

@Dao
interface RecipeDao {

    @get:Query("SELECT * from recipe_table")
    val all: List<Recipe>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg recipe: Recipe)

    @Query("DELETE from recipe_table")
    fun deleteAll()

    @Query("SELECT * from recipe_table WHERE id = :key")
    fun getRecipeWithId(key: Long): LiveData<Recipe>

    @Query("SELECT * FROM recipe_table WHERE name LIKE '%' || :recipeText || '%'")
    fun getRecipeList(recipeText: String?): List<Recipe>

}