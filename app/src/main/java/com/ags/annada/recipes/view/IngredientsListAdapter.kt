package com.ags.annada.recipes.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.recipes.R
import com.ags.annada.recipes.databinding.ItemIngredientBinding
import com.ags.annada.recipes.databinding.ItemRecipeBinding
import com.ags.annada.recipes.room.entities.Ingredient
import com.ags.annada.recipes.room.entities.Recipe
import com.ags.annada.recipes.viewmodel.IngredientViewModel
import com.ags.annada.recipes.viewmodel.RecipeViewModel

class IngredientsListAdapter (): RecyclerView.Adapter<IngredientsListAdapter.ViewHolder>() {
    private lateinit var ingredientsList: List<Ingredient>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemIngredientBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_ingredient,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsListAdapter.ViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    fun updateIngredientsList(ingredientsList: List<Ingredient>) {
        this.ingredientsList = ingredientsList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (::ingredientsList.isInitialized) ingredientsList.size else 0
    }


    class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = IngredientViewModel()

        fun bind(ingredient: Ingredient) {
            viewModel.bind(ingredient)

            binding.viewModel = viewModel
        }
    }
}

