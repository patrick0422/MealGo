package com.example.mealgo.ui.meal

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealgo.R
import com.example.mealgo.data.DataStoreRepository
import com.example.mealgo.data.meal.MealDataSource
import com.example.mealgo.data.meal.model.MealResponse
import com.example.mealgo.data.school.model.School
import com.example.mealgo.util.Constants
import com.example.mealgo.util.Constants.Companion.QUERY_API_KEY
import com.example.mealgo.util.Constants.Companion.QUERY_MEAL_DATE
import com.example.mealgo.util.Constants.Companion.QUERY_RESPONSE_TYPE
import com.example.mealgo.util.Constants.Companion.QUERY_SCHOOL_CODE
import com.example.mealgo.util.Constants.Companion.QUERY_SIDO_CODE
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    private var dateIndex: Long = 0

    private val _mealDate: MutableLiveData<String> = MutableLiveData(getDate(dateIndex))
    val mealDate: LiveData<String> get() = _mealDate

    fun getSchool() = viewModelScope.launch {
        dataStoreRepository.loadSchoolInfo.collect { school ->
            with(school) {
                _school.value = School(sidoCode, sidoName, schoolCode, schoolName, schoolLocation)
            }
        }
    }

    fun getMeal() = viewModelScope.launch {
        _mealList.value = NetworkResult.Loading()

        val queries = HashMap<String, String>()

        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_RESPONSE_TYPE] = "json"
        queries[QUERY_MEAL_DATE] = mealDate.value!!
        with(school.value!!) {
            queries[QUERY_SCHOOL_CODE] = schoolCode
            queries[QUERY_SIDO_CODE] = sidoCode
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

    fun onDateChange(view: View) {
        if (view.id == R.id.buttonLeft)
            _mealDate.value = getDate(--dateIndex)
        else if (view.id == R.id.buttonRight)
            _mealDate.value = getDate(++dateIndex)
        getMeal()
    }

    private fun getDate(dateIndex: Long): String =
        LocalDate.now().plusDays(dateIndex).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}