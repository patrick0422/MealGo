package com.example.mealgo.data.meal.model


import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("DDISH_NM")
    val dishName: String
)