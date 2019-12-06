package com.ags.annada.recipes.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ags.annada.recipes.room.entities.Ingredient

class StepViewModel : BaseViewModel() {
    private val step = MutableLiveData<String>()

    fun bind(instruction: String) {
        step.value = instruction
    }

    fun getStep(): MutableLiveData<String> {
        return step
    }
}