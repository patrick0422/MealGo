package com.example.mealgo.data.local.meal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealEntity(
    @PrimaryKey(autoGenerate = false)
    val mealDate: String,
    val breakFast: String?,
    val lunch: String?,
    val dinner: String?
)
