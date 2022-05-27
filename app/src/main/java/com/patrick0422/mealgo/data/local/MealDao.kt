package com.patrick0422.mealgo.data.local

import androidx.room.*
import com.patrick0422.mealgo.data.local.meal.model.MealEntity

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM MealEntity WHERE mealDate = :mealDate AND schoolName = :schoolName")
    suspend fun getMeal(mealDate: String, schoolName: String): MealEntity?

    @Delete
    suspend fun deleteMeal(mealEntity: MealEntity)
}
