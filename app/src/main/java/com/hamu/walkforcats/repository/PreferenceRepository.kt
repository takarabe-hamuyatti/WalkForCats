package com.hamu.walkforcats.repository

import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceRepository (sharedPreferences:SharedPreferences){

    val pref = sharedPreferences
    fun getDailyGoal(): Int? {
         return pref.getString(dailyGoalKey, "15000")?.toInt()
    }

    fun getMonthlyGoal():Int? {
        return pref.getString(monthlyGoalKey, "50000")?.toInt()
    }

    fun getDailyCount(): Int {
         return pref.getInt(dailyCountKey, 0)
    }

    fun getMonthlyCount(): Int {
        return  pref.getInt(monthlyCountKey, 0)
    }

    fun saveCount(dailyCount:Int?,monthlyCount:Int?){
        pref.edit {
            if (dailyCount != null) {
                putInt(dailyCountKey, dailyCount)
            }
            if (monthlyCount != null) {
                putInt(monthlyCountKey,monthlyCount)
            }
        }
    }

    fun clearCountOfTheDay(){
        pref.edit {
            putInt(dailyCountKey,0)
                .commit()
        }
    }
    fun clearCountOfTheMonth(){
        pref.edit {
            putInt(monthlyCountKey,0)
                .commit()
        }
    }

    companion object{
        val dailyGoalKey = "dailyGoal"
        val monthlyGoalKey ="monthlyGoal"
        val dailyCountKey = "dailyCount"
        val monthlyCountKey = "monthlyCount"
    }
}