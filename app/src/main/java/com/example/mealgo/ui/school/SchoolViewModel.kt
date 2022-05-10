package com.example.mealgo.ui.school

import android.util.Log
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.example.mealgo.util.NetworkResult
import com.example.mealgo.data.school.model.School
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.mealgo.data.school.SchoolDataSource
import com.example.mealgo.util.Constants.Companion.API_KEY
import com.example.mealgo.util.Constants.Companion.QUERY_API_KEY
import com.example.mealgo.util.Constants.Companion.QUERY_SCHOOL_NAME
import com.example.mealgo.util.Constants.Companion.QUERY_RESPONSE_TYPE
import com.example.mealgo.util.Constants.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val schoolDataSource: SchoolDataSource
) : ViewModel() {
    private val _schoolList = MutableLiveData<NetworkResult<List<School>>>()
    val schoolList: LiveData<NetworkResult<List<School>>> get() = _schoolList

    val searchQuery = MutableStateFlow("")

    val result = searchQuery
        .debounce(500)
        .distinctUntilChanged() // 중복 제거
        .flatMapLatest { keyword ->
            flow {
                getSchoolList(keyword)
                emit(keyword)
            }
        }
        .flowOn(Dispatchers.Default)
        .catch { e: Throwable -> e.stackTraceToString() }

    private fun getSchoolList(keyword: String) = viewModelScope.launch {
        _schoolList.value = NetworkResult.Loading()

        val queries = HashMap<String, String>()

        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_RESPONSE_TYPE] = "json"
        queries[QUERY_SCHOOL_NAME] = keyword

        _schoolList.value = try {
            val response = schoolDataSource.getSchoolList(queries)

            if (response.isSuccessful && response.body() != null) {
                val schoolResponse = response.body()!!
                val header = schoolResponse.schoolInfo[0].head[1].result
                if (header.code == "INFO-000")
                    NetworkResult.Success(schoolResponse.schoolInfo[1].schoolList)
                else {
                    val errorString = "${header.code}: ${header.message}"
                    NetworkResult.Error(errorString)
                }
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.stackTraceToString())
        }
    }
}