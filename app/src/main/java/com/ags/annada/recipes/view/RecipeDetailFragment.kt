package com.ags.annada.recipes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ags.annada.recipes.R
import com.ags.annada.recipes.databinding.FragmentRecipeDetailBinding
import com.ags.annada.recipes.room.entities.Ingredient
import com.ags.annada.recipes.viewmodel.RecipeDetailViewModel
import com.ags.annada.recipes.viewmodel.RecipeDetailViewModelFactory

class RecipeDetailFragment : Fragment() {

    companion object {
        val ARG_RECIPE_ID = "ARG_RECIPE_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding: FragmentRecipeDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_recipe_detail, container, false
        )

        val recipeId = arguments?.get(ARG_RECIPE_ID)

        val viewModel = ViewModelProviders.of(
            this,
            RecipeDetailViewModelFactory(activity as AppCompatActivity, recipeId as Long)
        ).get(RecipeDetailViewModel::class.java)

        viewModel.ingredientsList.observe(this, Observer {
            viewModel.ingredientsListAdapter.updateIngredientsList(it as List<Ingredient>) })

        viewModel.instructions.observe(this, Observer {
            viewModel.stepsAdapter.updateStepList(it as List<String>) })


        val recyclerView  = binding.ingredientList

        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val stepRecyclerView  = binding.steps

        stepRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        binding.viewModel = viewModel

        return binding.root
    }

}