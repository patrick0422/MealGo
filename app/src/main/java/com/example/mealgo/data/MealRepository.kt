package com.example.mealgo.data

import android.util.Log
import com.example.mealgo.data.local.meal.MealLocalDataSource
import com.example.mealgo.data.local.meal.model.MealEntity
import com.example.mealgo.data.remote.meal.MealRemoteDataSource
import com.example.mealgo.data.remote.meal.model.MealResponse
import com.example.mealgo.data.remote.school.model.School
import com.example.mealgo.util.Constants
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import javax.inject.Inject

class MealRepository @Inject constructor(
    val local: MealLocalDataSource,
    val remote: MealRemoteDataSource
) {
    suspend fun getMeal(mealDate: String, school: School): MealEntity? {
        val localMealResult = local.getMeal(mealDate, school.schoolName)

        return if (localMealResult == null) { // 해당 일자에 저장된 급식이 없는 경우
            Log.d(TAG, "getMeal: ${mealDate}에 저장된 정보가 없습니다.")
            requestAndSaveMeal(mealDate, school)
        } else {
            Log.d(TAG, "getMeal: ${mealDate}에 저장된 정보가 있습니다.")
            localMealResult
        }
    }

    private suspend fun requestAndSaveMeal(mealDate: String, school: School): MealEntity? {
        val queries = HashMap<String, String>()

        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_RESPONSE_TYPE] = "json"
        queries[Constants.QUERY_MEAL_DATE] = mealDate
        with(school) {
            queries[Constants.QUERY_SCHOOL_CODE] = schoolCode
            queries[Constants.QUERY_SIDO_CODE] = sidoCode
        }

        try {
            val response = remote.getMeal(queries)
            if (response.isSuccessful && response.body() != null) {
                val mealList = processMeal(response.body()!!)
                val mealEntity = MealEntity(
                    mealDate = mealDate,
                    schoolName = school.schoolName,
                    breakFast = mealList[0],
                    lunch = mealList[1],
                    dinner = mealList[2]
                )
                local.insertMeal(mealEntity)
                Log.d(TAG, "requestAndSaveMeal: ${mealDate}의 급식을 저장했습니다.")

                return mealEntity
            } else {
                Log.d(TAG, "requestAndSaveMeal: 저장 실패: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "requestAndSaveMeal: 저장 실패: ${e.stackTraceToString()}")
        }

        return null
    }

    private fun processMeal(body: MealResponse): List<String> {
        val result = mutableListOf("정보 없음", "정보 없음", "정보 없음")

        if (body.mealServiceDietInfo == null) return result

        val mealList = body.mealServiceDietInfo[1].mealList
        when(mealList.size) {
            1 -> { // 급식이 한 개 있는 경우 -> 중식만 있는 경우로 판단
                result[1] = processDish(mealList[0].dishName)
            }
            2 -> { // 급식이 두 개 있는 경우 -> 조식, 중식이 있는 경우로 판단
                result[0] = processDish(mealList[0].dishName)
                result[1] = processDish(mealList[1].dishName)
            }
            3 -> { // 급식이 세 개 있는 경우 -> 조식, 중식, 석식이 있는 경우로 판단
                result[0] = processDish(mealList[0].dishName)
                result[1] = processDish(mealList[1].dishName)
                result[2] = processDish(mealList[2].dishName)
            }
        }

        return result
    }

    private fun processDish(dishName: String): String = dishName
        .replace("<br/>", "\n")
        .replace(Regex("[^ㄱ-ㅎㅏ-ㅣ가-힣-\n&]"), "")
}