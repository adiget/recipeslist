package com.ags.annada.recipes.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.recipes.R
import com.ags.annada.recipes.databinding.ItemRecipeBinding
import com.ags.annada.recipes.room.entities.Recipe
import com.ags.annada.recipes.viewmodel.RecipeViewModel


class RecipeListAdapter(private val clickListener: RecipeListener) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    private lateinit var recipeList: List<Recipe>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecipeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recipe,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        holder.bind(recipeList[position], clickListener)
    }

    fun updateRecipeList(recipeList: List<Recipe>) {
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (::recipeList.isInitialized) recipeList.size else 0
    }


    class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = RecipeViewModel()

        fun bind(recipe: Recipe, clickListener: RecipeListener) {
            viewModel.bind(recipe)

            binding.viewModel = viewModel
            binding.clickListener = clickListener
        }
    }
}

class RecipeListener(val clickListener: (recipeId: Long) -> Unit) {
    fun onClick(id: Long) = clickListener(id)
}