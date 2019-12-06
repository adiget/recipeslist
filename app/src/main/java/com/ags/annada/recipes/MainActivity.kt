package com.ags.annada.recipes

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ags.annada.recipes.view.RecipeDetailFragment
import com.ags.annada.recipes.view.RecipeFragment


class MainActivity : AppCompatActivity(), RecipeFragment.OnRecipeSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecipeFragment.newInstance())
                .commitNow()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is RecipeFragment) {
            fragment.setOnRecipeSelectedListener(this)
        }
    }

    override fun onRecipeSelected(recipeId: Long) {
        val newFragment = RecipeDetailFragment()
        val args = Bundle()
        args.putLong(RecipeDetailFragment.ARG_RECIPE_ID, recipeId)
        newFragment.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
