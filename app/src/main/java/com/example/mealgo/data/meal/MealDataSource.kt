package com.example.mealgo.data.meal

import com.example.mealgo.data.meal.model.MealResponse
import retrofit2.Response
import javax.inject.Inject

class MealDataSource @Inject constructor(
    private val mealService: MealService
) {
    suspend fun getMeal(queries: Map<String, String>): Response<MealResponse> = mealService.getMealList(queries)
}