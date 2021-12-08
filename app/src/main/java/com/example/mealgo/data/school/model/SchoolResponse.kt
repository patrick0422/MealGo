package com.example.mealgo.data.school.model


import com.google.gson.annotations.SerializedName

data class SchoolResponse(
    @SerializedName("schoolInfo")
    val schoolInfo: List<SchoolInfo>
)