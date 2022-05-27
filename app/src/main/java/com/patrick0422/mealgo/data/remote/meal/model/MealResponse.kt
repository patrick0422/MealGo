package com.patrick0422.mealgo.data.remote.meal.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("mealServiceDietInfo")
    val mealServiceDietInfo: List<MealServiceDietInfo>?
)