package com.ags.annada.recipes.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ags.annada.recipes.room.daos.RecipeDao
import com.ags.annada.recipes.room.entities.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(RoomTypeConverters ::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase? {
            if (INSTANCE == null) {
                synchronized(RecipeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        RecipeDatabase::class.java, "recipe.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}