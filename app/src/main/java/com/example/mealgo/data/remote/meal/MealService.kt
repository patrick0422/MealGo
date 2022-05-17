package com.example.mealgo.data.remote.meal

import com.example.mealgo.data.remote.meal.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MealService {
    @GET("mealServiceDietInfo")
    suspend fun getMealList(@QueryMap queries: Map<String, String>): Response<MealResponse>
}