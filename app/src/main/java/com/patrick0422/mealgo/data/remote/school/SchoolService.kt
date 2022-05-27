package com.patrick0422.mealgo.data.remote.school

import com.patrick0422.mealgo.data.remote.school.model.SchoolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SchoolService {
    @GET("schoolInfo")
    suspend fun getSchoolList(@QueryMap queries: Map<String, String>): Response<SchoolResponse>
}