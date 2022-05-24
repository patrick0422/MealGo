package com.example.mealgo.data

import com.example.mealgo.data.local.meal.MealLocalDataSource
import com.example.mealgo.data.remote.meal.MealRemoteDataSource
import javax.inject.Inject

class MealRepository @Inject constructor(
    val local: MealLocalDataSource,
    val remote: MealRemoteDataSource
)