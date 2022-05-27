package com.patrick0422.mealgo.data.remote.school.model

import com.google.gson.annotations.SerializedName

data class Head(
    @SerializedName("list_total_count")
    val listTotalCount: Int,
    @SerializedName("RESULT")
    val result: Result
)