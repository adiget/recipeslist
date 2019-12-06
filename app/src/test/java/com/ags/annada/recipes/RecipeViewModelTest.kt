package com.ags.annada.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ags.annada.recipes.room.entities.Recipe
import com.ags.annada.recipes.viewmodel.RecipeViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RecipeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: RecipeViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = RecipeViewModel()
    }

    @Test
    fun `test members updated when model changes`() {
        val recipe = Recipe(1, "dummy_recipe", null, null, null,null)
        sut.bind(recipe)

        Assert.assertEquals(
            "id should be 1",
            1L,
            sut.id.value
        )

        Assert.assertEquals(
            "title should be dummy_recipe",
            "dummy_recipe",
            sut.getTitle().value
        )
    }

    @Test
    fun `test livedata notifies when it is observed`() {
        val observer = mock<Observer<String>>()
        sut.getTitle().observeForever(observer)
        sut.getImageUrl().observeForever(observer)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        val recipe = Recipe(1, "dummy_recipe", null, "dummy_url", null,null)
        sut.bind(recipe)

        argumentCaptor.run {
            Mockito.verify(observer, Mockito.times(2)).onChanged(capture())
        }
    }

}