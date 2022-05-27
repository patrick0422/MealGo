package com.patrick0422.mealgo.data.remote.meal

import com.patrick0422.mealgo.data.remote.meal.model.MealResponse
import retrofit2.Response
import javax.inject.Inject

class MealRemoteDataSource @Inject constructor(
    private val mealService: MealService
) {
    suspend fun getMeal(queries: Map<String, String>): Response<MealResponse> = mealService.getMealList(queries)
}