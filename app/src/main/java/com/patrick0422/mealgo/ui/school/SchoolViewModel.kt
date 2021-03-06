package com.patrick0422.mealgo.ui.school

import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.patrick0422.mealgo.util.NetworkResult
import com.patrick0422.mealgo.data.remote.school.model.School
import dagger.hilt.android.lifecycle.HiltViewModel
import com.patrick0422.mealgo.data.remote.school.SchoolDataSource
import com.patrick0422.mealgo.util.Constants.Companion.API_KEY
import com.patrick0422.mealgo.util.Constants.Companion.QUERY_API_KEY
import com.patrick0422.mealgo.util.Constants.Companion.QUERY_SCHOOL_NAME
import com.patrick0422.mealgo.util.Constants.Companion.QUERY_RESPONSE_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val schoolDataSource: SchoolDataSource
) : ViewModel() {
    private val _schoolList = MutableLiveData<NetworkResult<List<School>>>()
    val schoolList: LiveData<NetworkResult<List<School>>> get() = _schoolList




    fun getSchoolList(keyword: String) = viewModelScope.launch {
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