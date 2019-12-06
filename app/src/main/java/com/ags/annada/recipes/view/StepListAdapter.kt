package com.ags.annada.recipes.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ags.annada.recipes.R
import com.ags.annada.recipes.databinding.ItemIngredientBinding
import com.ags.annada.recipes.databinding.ItemRecipeBinding
import com.ags.annada.recipes.databinding.ItemStepBinding
import com.ags.annada.recipes.room.entities.Ingredient
import com.ags.annada.recipes.room.entities.Recipe
import com.ags.annada.recipes.viewmodel.IngredientViewModel
import com.ags.annada.recipes.viewmodel.RecipeViewModel
import com.ags.annada.recipes.viewmodel.StepViewModel

class StepListAdapter() : RecyclerView.Adapter<StepListAdapter.ViewHolder>() {
    private lateinit var steps: List<String>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemStepBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_step,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepListAdapter.ViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    fun updateStepList(steps: List<String>) {
        this.steps = steps
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (::steps.isInitialized) steps.size else 0
    }


    class ViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = StepViewModel()

        fun bind(step: String) {
            viewModel.bind(step)

            binding.viewModel = viewModel
        }
    }
}

