package com.ags.annada.recipes.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ags.annada.recipes.room.entities.Ingredient

class IngredientViewModel : BaseViewModel() {
    private val name = MutableLiveData<String>()
    private val quantity = MutableLiveData<String>()

    fun bind(ingredientObj: Ingredient) {
        name.value = ingredientObj.name
        quantity.value = ingredientObj.quantity
    }

    fun getName(): MutableLiveData<String> {
        return name
    }

    fun getQuantity(): MutableLiveData<String> {
        return quantity
    }


}