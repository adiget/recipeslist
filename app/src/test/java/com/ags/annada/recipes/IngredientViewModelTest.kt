package com.ags.annada.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ags.annada.recipes.room.entities.Ingredient
import com.ags.annada.recipes.viewmodel.IngredientViewModel
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
class IngredientViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: IngredientViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = IngredientViewModel()
    }

    @Test
    fun `test members updated when model changes`() {
        val ingredient = Ingredient("dummy_name", "dummy_quantity", "dummy_type")
        sut.bind(ingredient)

        Assert.assertEquals(
            "name should be dummy_name",
            "dummy_name",
            sut.getName().value
        )

        Assert.assertEquals(
            "quantity should be dummy_quantity",
            "dummy_quantity",
            sut.getQuantity().value
        )
    }

    @Test
    fun `test livedata notifies when it is observed`() {
        val observer = mock<Observer<String>>()
        sut.getName().observeForever(observer)
        sut.getQuantity().observeForever(observer)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        val ingredient = Ingredient("dummy_name", "dummy_quantity", "dummy_type")
        sut.bind(ingredient)

        argumentCaptor.run {
            Mockito.verify(observer, Mockito.times(2)).onChanged(capture())
        }
    }

}