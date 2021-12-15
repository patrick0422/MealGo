package com.example.mealgo.data.meal.model


import com.google.gson.annotations.SerializedName

data class MealServiceDietInfo(
    @SerializedName("head")
    val head: List<Head>,
    @SerializedName("row")
    val mealList: List<Meal>
)