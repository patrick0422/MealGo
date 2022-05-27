package com.patrick0422.mealgo.data.remote.school.model

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("ATPT_OFCDC_SC_CODE")
    val sidoCode: String,
    @SerializedName("ATPT_OFCDC_SC_NM")
    val sidoName: String,
    @SerializedName("SD_SCHUL_CODE")
    val schoolCode: String,
    @SerializedName("SCHUL_NM")
    val schoolName: String,
    @SerializedName("ORG_RDNMA")
    val schoolLocation: String
)