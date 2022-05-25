package com.example.mealgo.ui.meal

import android.util.Log
import androidx.lifecycle.*
import com.example.mealgo.data.DataStoreRepository
import com.example.mealgo.data.MealRepository
import com.example.mealgo.data.local.meal.model.MealEntity
import com.example.mealgo.data.remote.meal.MealRemoteDataSource
import com.example.mealgo.data.remote.meal.model.MealResponse
import com.example.mealgo.data.remote.school.model.School
import com.example.mealgo.util.Constants
import com.example.mealgo.util.Constants.Companion.QUERY_API_KEY
import com.example.mealgo.util.Constants.Companion.QUERY_MEAL_DATE
import com.example.mealgo.util.Constants.Companion.QUERY_RESPONSE_TYPE
import com.example.mealgo.util.Constants.Companion.QUERY_SCHOOL_CODE
import com.example.mealgo.util.Constants.Companion.QUERY_SIDO_CODE
import com.example.mealgo.util.Constants.Companion.TAG
import com.example.mealgo.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    // Local Data
    private val _localMeal: MutableLiveData<MealEntity> = MutableLiveData()
    val localMeal: LiveData<MealEntity> get() = _localMeal

    private fun getLocalMeal() = viewModelScope.launch {
        _localMeal.value = mealRepository.local.getMeal(getDate())
    }

    fun insertMeal(mealList: List<String>) = viewModelScope.launch {
        val mealEntity = MealEntity(
            getDate(),
            mealList[0],
            mealList[1],
            mealList[2]
        )

        mealRepository.local.insertMeal(mealEntity)
    }

    // Remote Data
    private val _mealList: MutableLiveData<NetworkResult<List<String>>> = MutableLiveData()
    val mealList: LiveData<NetworkResult<List<String>>> get() = _mealList

    private val _school: MutableLiveData<School> = MutableLiveData()
    val school: LiveData<School> get() = _school

    private var dateIndex: Long = 0

    private val _mealDate: MutableLiveData<String> = MutableLiveData(getDate())
    val mealDate: LiveData<String> get() = _mealDate

    val mealDateFormatted: String get() {
        val targetDate = LocalDate.now().plusDays(dateIndex)

        val dayOfWeekList = listOf("월", "화", "수", "목", "금", "토", "일")
        val dayOfWeek = targetDate.dayOfWeek.value

        var result = targetDate.format(DateTimeFormatter.ofPattern("M월 d일 (${dayOfWeekList[dayOfWeek - 1]})"))
        if (targetDate == LocalDate.now()) result += "-오늘"

        return result
    }

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
            val response = mealRepository.remote.getMeal(queries)
            Log.d(TAG, "getMeal(): ${response.raw()}")

            if (response.isSuccessful && response.body() != null) {
                val mealList = processMeal(response.body()!!)
                insertMeal(mealList)
                NetworkResult.Success(mealList)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }

    private fun processMeal(body: MealResponse): List<String> =
        if(body.mealServiceDietInfo == null) {
            listOf(
                "정보 없음",
                "정보 없음",
                "정보 없음",
            )
        } else {
            body.mealServiceDietInfo[1].mealList.map { rawMealList ->
                rawMealList.dishName
                    .replace("<br/>", "\n")
                    .replace(Regex("[^ㄱ-ㅎㅏ-ㅣ가-힣-\n&]"), "")
            }
        }

    fun onDateChange(index: Int) {
        if(index == 0) {
            dateIndex = 0
            _mealDate.value = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        } else {
            dateIndex += index
            _mealDate.value = getDate()
        }
        Log.d(TAG, "onDateChange: dateIndex=${dateIndex}")
        getMeal()
    }
    private fun getDate(): String =
        LocalDate.now().plusDays(dateIndex).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}