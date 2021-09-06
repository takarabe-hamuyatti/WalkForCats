package com.hamu.walkforcats.repository

import android.content.SharedPreferences
import androidx.core.content.edit

interface PreferenceRepository {

    fun getDailyGoal(): Int?

    fun getMonthlyGoal():Int?
    fun getDailyCount(): Int

    fun getMonthlyCount(): Int

    fun saveCount(dailyCount:Int?,monthlyCount:Int?)

    fun clearCountOfTheDay()

    fun clearCountOfTheMonth()

    fun isUseDemoData(): Boolean

}