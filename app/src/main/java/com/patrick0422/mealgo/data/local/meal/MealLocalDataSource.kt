package com.patrick0422.mealgo.data.local.meal

import com.patrick0422.mealgo.data.local.MealDao
import com.patrick0422.mealgo.data.local.meal.model.MealEntity
import javax.inject.Inject


class MealLocalDataSource @Inject constructor(
    private val mealDao: MealDao
) {
    suspend fun insertMeal(mealEntity: MealEntity) = mealDao.insertMeal(mealEntity)

    suspend fun deleteMeal(mealEntity: MealEntity) = mealDao.deleteMeal(mealEntity)

    suspend fun getMeal(mealDate: String, schoolName: String) = mealDao.getMeal(mealDate, schoolName)
}