package com.ags.annada.recipes

import com.ags.annada.recipes.api.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    lateinit var service: ApiService

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mobile.asosservices.com/sampleapifortest/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    @Test
    fun should_callServiceWithCoroutine() {
        runBlocking {
            val recipes = service.getRecipes()

            Assert.assertTrue(recipes.isNotEmpty())
        }
    }
}