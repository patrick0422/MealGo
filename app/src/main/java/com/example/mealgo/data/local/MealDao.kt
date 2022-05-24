package com.example.mealgo.data.local

import androidx.room.*
import com.example.mealgo.data.local.meal.model.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM MealEntity WHERE mealDate = :mealDate")
    fun getMeal(mealDate: String): Flow<MealEntity>

    @Delete
    suspend fun deleteMeal(mealEntity: MealEntity)
}
