package com.example.mealgo.data.remote.school.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("CODE")
    val code: String,
    @SerializedName("MESSAGE")
    val message: String
)