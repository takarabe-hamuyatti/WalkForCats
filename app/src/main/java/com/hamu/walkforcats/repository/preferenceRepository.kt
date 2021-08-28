package com.hamu.walkforcats.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class preferenceRepository (){

    fun getDailyGoalFromPreference(pref :SharedPreferences): Float? {
         return pref.getString(dailyGoalKey, "15000")?.toFloat()
    }

    fun getWeeklyGoalFromPreference(pref :SharedPreferences):Float? {
        return pref.getString(monthlyGoalKey, "50000")?.toFloat()
    }

    fun getDailyCountFromPreference(pref :SharedPreferences): Int {
         return pref.getInt(dailyCountKey, 0)
    }

    fun getWeeklyCountFromPreference(pref :SharedPreferences): Int {
        return  pref.getInt(monthlyCountKey, 0)
    }

    fun saveCount(pref :SharedPreferences,dailyCount:Int?,monthlyCount:Int?){
        pref.edit {
            if (dailyCount != null) {
                putInt(dailyCountKey, dailyCount)
            }
            if (monthlyCount != null) {
                putInt(monthlyCountKey,monthlyCount)
                    .commit()
            }
        }
    }

    companion object{
        val dailyGoalKey = "dailyGoal"
        val monthlyGoalKey ="monthlyGoal"
        val dailyCountKey = "dailyCount"
        val monthlyCountKey = "monthlyCount"
    }
}