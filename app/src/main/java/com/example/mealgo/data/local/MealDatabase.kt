package com.example.mealgo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealgo.data.local.meal.model.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}