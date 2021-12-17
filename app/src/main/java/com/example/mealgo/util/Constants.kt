package com.example.mealgo.util

class Constants {
    companion object {
        const val TAG = "MealGo"

        const val BASE_URL = "https://open.neis.go.kr/hub/"
        const val API_KEY = "2184fd7c40cc46d19af258829cb14159"


        // Request Params
        const val QUERY_API_KEY = "KEY"
        const val QUERY_RESPONSE_TYPE = "Type"
        const val QUERY_SCHOOL_NAME = "SCHUL_NM"


        const val QUERY_SIDO_CODE = "ATPT_OFCDC_SC_CODE"
        const val QUERY_SCHOOL_CODE = "SD_SCHUL_CODE"
        const val QUERY_MEAL_DATE = "MLSV_YMD"

        // DataStore Params
        const val PREFERENCES_NAME = "MealGoStore"

        const val PREFERENCES_SIDO_CODE = "sidoCode"
        const val PREFERENCES_SIDO_NAME = "sidoName"
        const val PREFERENCES_SCHOOL_CODE = "schoolCode"
        const val PREFERENCES_SCHOOL_NAME = "schoolName"
        const val PREFERENCES_SCHOOL_LOCATION = "schoolLocation"

        const val PREFERENCES_NO_INFO = "정보 없음"
    }
}