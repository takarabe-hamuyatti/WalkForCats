package com.hamu.walkforcats.utils

class UniqueId {
    companion object{
        //共有プリファレンス
        const val DAILY_GOAL_KEY = "dailyGoal"
        const val MONTHLY_GOAL_KEY ="monthlyGoal"
        const val DAILY_COUNT_KEY = "dailyCount"
        const val MONTHLY_COUNT_KEY = "monthlyCount"
        const val DEMO_DATA_KEY = "demoData"
        const val CHANGE_CAT_KEY = "changeCat"
        const val CHECK_FIRST_TIME_OF_HISTRY_KEY = "checkFirstTimeOfHistry"
        const val CHECK_FIRST_TIME_OF_STEPCOUNT_KEY = "checkFirstTimeOfStepCount"
        const val FIRST_INIT_KEY = "firstInit"

        //database 名
        const val MONTHLYINFO_DATABASE_NAME = "monthlyInfoDatabase"
        // 過去の緯度軽度保存用
        const val PAST_LOCATION_KEY = 1

        //ApiKey todo 隠し方を探す
        const val OPEN_WEATHER_API_KEY = "de9c3d7a7750b7c5a3d9f9691bc274e8"
    }
}
