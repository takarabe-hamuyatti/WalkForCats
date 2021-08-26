package com.hamu.walkforcats.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class preferenceRepository (){

    fun getDailyGoalFromPreference(pref :SharedPreferences): String? {
         return pref.getString(dailyGoalKey, "15000")
    }

    fun getWeeklyGoalFromPreference(pref :SharedPreferences):String? {
        return pref.getString(weeklyGoalKey, "50000")
    }

    fun getDailyCountFromPreference(pref :SharedPreferences): Int {
         return pref.getInt(dailyCountKey, 0)
    }

    fun getWeeklyCountFromPreference(pref :SharedPreferences): Int {
        return  pref.getInt(weeklyCountKey, 0)
    }

    fun saveCount(pref :SharedPreferences,dailyCount:Int?,weeklyCount:Int?){
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