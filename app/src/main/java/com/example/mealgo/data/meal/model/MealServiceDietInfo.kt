package com.example.mealgo.data.meal.model


import com.google.gson.annotations.SerializedName

data class MealServiceDietInfo(
    @SerializedName("row")
    val mealList: List<Meal>
)