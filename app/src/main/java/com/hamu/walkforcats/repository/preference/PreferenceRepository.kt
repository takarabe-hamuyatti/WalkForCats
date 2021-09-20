package com.hamu.walkforcats.repository.preference


interface PreferenceRepository {

    fun getDailyGoal(): Int?

    fun getMonthlyGoal():Int?

    fun getDailyCount(): Int

    fun getMonthlyCount():Int

    fun saveCount(dailyCount:Int?,monthlyCount:Int?)

    fun clearCountOfTheDay()

    fun clearCountOfTheMonth()

    fun isUseDemoData(): Boolean

    fun isCangeCat() :Boolean

    fun checkFirstTimeOfHistry():Boolean

    fun changeIsFirstTimeOfHistry()

}