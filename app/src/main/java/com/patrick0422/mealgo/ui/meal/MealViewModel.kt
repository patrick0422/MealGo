package com.patrick0422.mealgo.ui.meal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrick0422.mealgo.data.DataStoreRepository
import com.patrick0422.mealgo.data.MealRepository
import com.patrick0422.mealgo.data.remote.school.model.School
import com.patrick0422.mealgo.util.Constants.Companion.TAG
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

    // Remote Data
    private val _mealList: MutableLiveData<List<String>> = MutableLiveData()
    val mealList: LiveData<List<String>> get() = _mealList

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
        val mealEntity = mealRepository.getMeal(getDate(), school.value!!) ?: return@launch

        _mealList.value = listOf(
            mealEntity.breakFast,
            mealEntity.lunch,
            mealEntity.dinner
        )
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