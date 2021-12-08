package com.example.mealgo.data.school

import com.example.mealgo.data.school.model.SchoolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SchoolService {
    @GET("schoolInfo")
    suspend fun getSchoolList(@QueryMap queries: Map<String, String>): Response<SchoolResponse>
}