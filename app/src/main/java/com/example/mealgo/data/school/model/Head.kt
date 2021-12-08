package com.example.mealgo.data.school.model


import com.google.gson.annotations.SerializedName

data class Head(
    @SerializedName("list_total_count")
    val listTotalCount: Int,
    @SerializedName("RESULT")
    val result: Result
)