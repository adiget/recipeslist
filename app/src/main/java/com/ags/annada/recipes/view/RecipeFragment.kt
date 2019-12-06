package com.ags.annada.recipes.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ags.annada.recipes.R
import com.ags.annada.recipes.databinding.RecipeFragmentBinding
import com.ags.annada.recipes.viewmodel.RecipeListViewModel
import com.ags.annada.recipes.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RecipeFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeFragment()
    }

    internal lateinit var callback: OnRecipeSelectedListener

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var binding: RecipeFragmentBinding
    private var errorSnackbar: Snackbar? = null
    private lateinit var recyclerView: RecyclerView

    fun setOnRecipeSelectedListener(callback: OnRecipeSelectedListener) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity))
            .get(RecipeListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        viewModel.selectItemEvent.observe(this, Observer {
            if (it != null) {
                Log.d("SELECTED ID", "selected=${it}");
                callback.onRecipeSelected(it)
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_fragment, container, false)

        val staggeredLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView = binding.recipeList

        recyclerView.layoutManager = staggeredLayoutManager

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint = ""

                item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(menuItem: MenuItem?): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(menuItem: MenuItem?): Boolean {
                        viewModel.loadData()
                        return true
                    }
                })

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextChange(newText: String): Boolean {
                        return false
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.getRecipesFromDb(query);
                        return false
                    }
                })

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }


    interface OnRecipeSelectedListener {
        fun onRecipeSelected(recipeId: Long)
    }
}