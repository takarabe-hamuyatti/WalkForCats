package com.hamu.walkforcats.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class preferenceRepository ( sharedPreferences:SharedPreferences){

    val pref = sharedPreferences
    fun getDailyGoalFromPreference(): Float? {
         return pref.getString(dailyGoalKey, "15000")?.toFloat()
    }

    fun getMonthlyGoalFromPreference():Float? {
        return pref.getString(monthlyGoalKey, "50000")?.toFloat()
    }

    fun getDailyCountFromPreference(): Int {
         return pref.getInt(dailyCountKey, 0)
    }

    fun getMonthlyCountFromPreference(): Int {
        return  pref.getInt(monthlyCountKey, 0)
    }

    fun saveCount(dailyCount:Int?,monthlyCount:Int?){
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

    fun clearCountOfTheDay(){
        pref.edit {
            putInt(dailyCountKey,0)
        }
    }

    companion object{
        val dailyGoalKey = "dailyGoal"
        val monthlyGoalKey ="monthlyGoal"
        val dailyCountKey = "dailyCount"
        val monthlyCountKey = "monthlyCount"
    }
}