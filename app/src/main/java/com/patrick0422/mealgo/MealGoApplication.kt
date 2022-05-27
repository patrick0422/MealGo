package com.patrick0422.mealgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MealGoApplication: Application()

/** 추가할 기능들
 * 1. 기상 시각에 맞춰 푸시 알림으로 급식 전송
 * 2. Room 기반 페이징으로 스와이프 해서 급식 넘기기
 */