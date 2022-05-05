package com.example.mealgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MealGoApplication: Application() {}

/* 추가할 기능들

1. 학교 검색에 Flow로 Instant Search 구현
2. Room에 급식 데이터 저장
3. Room 기반 페이징으로 스와이프 해서 급식 넘기기
4. 기상 시각에 맞춰 푸시 알림으로 급식 전송
 */
/* 이슈

1. 학교 변경 후 급식 업데이트 안됨
 */