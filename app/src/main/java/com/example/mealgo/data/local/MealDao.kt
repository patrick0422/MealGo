package com.example.mealgo.data.local

import androidx.room.*
import com.example.mealgo.data.local.meal.model.MealEntity
@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM MealEntity WHERE mealDate = :mealDate")
    suspend fun getMeal(mealDate: String): MealEntity

    @Delete
    suspend fun deleteMeal(mealEntity: MealEntity)
}
