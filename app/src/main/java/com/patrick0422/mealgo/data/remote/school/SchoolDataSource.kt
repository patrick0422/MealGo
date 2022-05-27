package com.patrick0422.mealgo.data.remote.school

import com.patrick0422.mealgo.data.remote.school.model.SchoolResponse
import retrofit2.Response
import javax.inject.Inject

class SchoolDataSource @Inject constructor(
    private val schoolService: SchoolService
) {
    suspend fun getSchoolList(queries: HashMap<String, String>): Response<SchoolResponse> = schoolService.getSchoolList(queries)
}