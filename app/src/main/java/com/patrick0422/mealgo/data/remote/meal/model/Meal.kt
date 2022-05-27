package com.patrick0422.mealgo.data.remote.meal.model

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("DDISH_NM")
    val dishName: String
)