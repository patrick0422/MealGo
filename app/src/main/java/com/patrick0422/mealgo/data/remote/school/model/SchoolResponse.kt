package com.patrick0422.mealgo.data.remote.school.model

import com.google.gson.annotations.SerializedName

data class SchoolResponse(
    @SerializedName("schoolInfo")
    val schoolInfo: List<SchoolInfo>
)