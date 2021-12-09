package com.example.mealgo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealgo.data.DataStoreRepository
import com.example.mealgo.data.school.model.School
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _school: MutableLiveData<School> = MutableLiveData()
    val school: LiveData<School> get() = _school

    fun getSchool() = viewModelScope.launch {
        dataStoreRepository.loadSchoolInfo.collect { school ->
            with(school) {
                _school.value = School(sidoCode, sidoName, schoolCode, schoolName, schoolLocation)
            }
        }
    }
}