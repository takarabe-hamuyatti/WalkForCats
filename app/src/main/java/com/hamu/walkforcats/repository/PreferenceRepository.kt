package com.hamu.walkforcats.repository


interface PreferenceRepository {

    fun getDailyGoal(): Int?

    fun getMonthlyGoal():Int?

    fun getDailyCount(): Int

    fun getMonthlyCount(): Int

    fun saveCount(dailyCount:Int?,monthlyCount:Int?)

    fun clearCountOfTheDay()

    fun clearCountOfTheMonth()

    fun isUseDemoData(): Boolean

    fun isUseMoreCat() :Boolean

}