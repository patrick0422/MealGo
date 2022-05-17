package com.example.mealgo.data.remote.school.model


import com.google.gson.annotations.SerializedName

data class SchoolInfo(
    @SerializedName("head")
    val head: List<Head>,
    @SerializedName("row")
    val schoolList: List<School>
)