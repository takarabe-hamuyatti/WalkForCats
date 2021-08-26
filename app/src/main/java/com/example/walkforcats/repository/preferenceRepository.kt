package com.example.walkforcats.repository

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class preferenceRepository (cont :Context){
    val pref = PreferenceManager.getDefaultSharedPreferences(cont)

    fun getDailyGoalFromPreference(): String? {
         return pref.getString(dailyGoalKey, "15000")
    }

    fun getWeeklyGoalFromPreference():String? {
        return pref.getString(weeklyGoalKey, "50000")
    }

    fun getDailyCountFromPreference(): Int {
         return pref.getInt(dailyCountKey, 200)
    }

    fun getWeeklyCountFromPreference(): Int {
        return  pref.getInt(weeklyCountKey, 200)
    }

    fun saveCount(dailyCount:Int?,weeklyCount:Int?){
        pref.edit {
            if (dailyCount != null) {
                putInt(dailyCountKey, dailyCount)
            }
            if (weeklyCount != null) {
                putInt(weeklyCountKey,weeklyCount)
                    .commit()
            }
        }
    }

    companion object{
        val dailyGoalKey = "dailyGoal"
        val weeklyGoalKey ="weeklyGoal"
        val dailyCountKey = "dailyCount"
        val weeklyCountKey = "weeklyCount"
    }
}