package com.hamu.walkforcats.repository.preference


interface PreferenceRepository {

    fun getDailyGoal(): Int?

    fun getMonthlyGoal():String

    fun getDailyCount(): Int

    fun getMonthlyCount(): String

    fun saveCount(dailyCount:Int?,monthlyCount:Int?)

    fun clearCountOfTheDay()

    fun clearCountOfTheMonth()

    fun isUseDemoData(): Boolean

    fun isCangeCat() :Boolean

    fun checkFirstTimeOfHistry():Boolean

    fun changeIsFirstTimeOfHistry()

}