package com.ags.annada.recipes.viewmodel

import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.ags.annada.recipes.R
import com.ags.annada.recipes.api.ApiService
import com.ags.annada.recipes.room.daos.RecipeDao
import com.ags.annada.recipes.room.entities.Recipe
import com.ags.annada.recipes.utils.SingleLiveEvent
import com.ags.annada.recipes.view.RecipeListAdapter
import com.ags.annada.recipes.view.RecipeListener
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RecipeListViewModel(private val recipeDao: RecipeDao) : BaseViewModel() {
    @Inject
    lateinit var api: ApiService
    internal val selectItemEvent = SingleLiveEvent<Long>()

    val recipeListAdapter: RecipeListAdapter = RecipeListAdapter(RecipeListener { recipeId ->
        selectItemEvent.value = recipeId
    })

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadData() }

    private val viewModelJob = Job()
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        onRetrieveRecipeListFinish()
        when (error) {
            is Exception -> onRetrieveRecipeListError()
        }
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + errorHandler)
    private val bgDispatcher = Dispatchers.IO

    init {
        loadData()
    }

    fun loadData() {
        uiScope.launch {
            onRetrieveRecipeListStart()

            //removeExpiredData()

            var recipeList = getDbRecipes()


            if (recipeList?.isNotEmpty() == false) {

                recipeList = api.getRecipes()

                recipeList.let { insertAll(it) }
            }

            recipeList = getDbRecipes()

            recipeList?.let { onRetrieveRecipeListSuccess(it) }

            onRetrieveRecipeListFinish()
        }
    }

    fun getRecipesFromDb(query: String){
        uiScope.launch {
            val recipeList = getDbRecipesWithSearch(query)

            recipeList?.let { recipeListAdapter.updateRecipeList(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

    private suspend fun onRetrieveRecipeListStart() {
        withContext(Dispatchers.Main) {
            loadingVisibility.value = View.VISIBLE
            errorMessage.value = null
        }
    }

    private fun onRetrieveRecipeListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRecipeListSuccess(recipeList: List<Recipe>) {
        recipeListAdapter.updateRecipeList(recipeList)
    }

    private fun onRetrieveRecipeListError() {
        errorMessage.value = R.string.recipe_error
    }

    private suspend fun getDbRecipes(): List<Recipe>? {
        return withContext(bgDispatcher) {
            recipeDao.all
        }
    }

    private suspend fun getDbRecipesWithSearch( query: String): List<Recipe>? {
        return withContext(bgDispatcher) {
            recipeDao.getRecipeList(query)
        }
    }


    private suspend fun removeExpiredData(){
        withContext(bgDispatcher) {
            val expireTime = TimeUnit.HOURS.toHours(1)
            val recipe: Recipe? = recipeDao.all?.get(0)

            if (recipe != null) {
                if (System.currentTimeMillis() - recipe.createdTime > expireTime) {
                    recipeDao.deleteAll()
                }
            }
        }
    }

    private suspend fun insertAll(recipes: List<Recipe>) {
        return withContext(bgDispatcher) {
            recipeDao.insertAll(*recipes.toTypedArray())
        }
    }
}