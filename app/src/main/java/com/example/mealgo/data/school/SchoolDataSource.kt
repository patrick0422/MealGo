package com.example.mealgo.data.school

import com.example.mealgo.data.school.model.SchoolResponse
import com.example.mealgo.util.Constants.Companion.API_KEY
import com.example.mealgo.util.NetworkResult
import retrofit2.Response
import javax.inject.Inject

class SchoolDataSource @Inject constructor(
    private val schoolService: SchoolService
) {
    suspend fun getSchoolList(queries: HashMap<String, String>): Response<SchoolResponse> = schoolService.getSchoolList(queries)
}