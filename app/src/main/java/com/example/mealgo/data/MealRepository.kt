package com.example.mealgo.data

import com.example.mealgo.data.local.meal.MealLocalDataSource
import com.example.mealgo.data.remote.meal.MealRemoteDataSource

class MealRepository(
    val local: MealLocalDataSource,
    val remote: MealRemoteDataSource
)