package com.example.mealgo.ui.meal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealgo.data.DataStoreRepository
import com.example.mealgo.data.meal.MealDataSource
import com.example.mealgo.data.meal.model.MealResponse
import com.example.mealgo.data.school.model.School
import com.example.mealgo.util.Constants
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealDataSource: MealDataSource,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _mealList: MutableLiveData<NetworkResult<List<String>>> = MutableLiveData()
    val mealList: LiveData<NetworkResult<List<String>>> get() = _mealList

    private val _school: MutableLiveData<School> = MutableLiveData()
    val school: LiveData<School> get() = _school

    fun getSchool() = viewModelScope.launch {
        dataStoreRepository.loadSchoolInfo.collect { school ->
            with(school) {
                _school.value = School(sidoCode, sidoName, schoolCode, schoolName, schoolLocation)
            }
        }
    }

    fun getMealList(mealDate: String) = viewModelScope.launch {
        val queries = HashMap<String, String>()

        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_RESPONSE_TYPE] = "json"

        queries[Constants.QUERY_MEAL_DATE] = mealDate

        with(school.value!!) {
            queries[Constants.QUERY_SCHOOL_CODE] = schoolCode
            queries[Constants.QUERY_SIDO_CODE] = sidoCode
            Log.d(TAG, "getMealList: $sidoCode, $schoolCode")
        }

        _mealList.value = try {
            val response = mealDataSource.getMeal(queries)
            Log.d(TAG, "getMealList: ${response.raw()}")

            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(processMeal(response.body()!!))
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    private fun processMeal(body: MealResponse): List<String> =
        body.mealServiceDietInfo[1].mealList.map { rawMealList ->
            rawMealList.dishName
                .replace("<br/>", "\n")
                .replace(Regex("[^ㄱ-ㅎㅏ-ㅣ가-힣-\n]"), "")
        }
}