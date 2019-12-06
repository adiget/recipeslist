package com.ags.annada.recipes.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "ingredients") var ingredients: List<Ingredient>?,
    @ColumnInfo(name = "imageURL") var imageURL: String?,
    @ColumnInfo(name = "steps") var steps: List<String>?,
    @ColumnInfo(name = "timers") var timers: List<Int>?,
    @ColumnInfo(name = "createdTime") val createdTime : Long = System.currentTimeMillis()
)
{
    constructor() : this(null, "", null, null, null, null)
}