package com.patrick0422.mealgo.data.local.meal.model

import androidx.room.Entity

@Entity(primaryKeys = ["mealDate", "schoolName"])
data class MealEntity(
    val mealDate: String,
    val schoolName: String,
    val breakFast: String,
    val lunch: String,
    val dinner: String
)
