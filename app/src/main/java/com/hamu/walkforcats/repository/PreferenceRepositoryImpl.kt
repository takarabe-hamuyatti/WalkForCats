package com.hamu.walkforcats.repository

import android.content.SharedPreferences
import androidx.core.content.edit


class PreferenceRepositoryImpl(
    private val pref:SharedPreferences
):PreferenceRepository {
    override fun getDailyGoal(): Int? {
        return pref.getString(dailyGoalKey, "15000")?.toInt()
    }

    override fun getMonthlyGoal():Int? {
        return pref.getString(monthlyGoalKey, "50000")?.toInt()
    }

    override fun getDailyCount(): Int {
        return pref.getInt(dailyCountKey, 0)
    }

    override fun getMonthlyCount(): Int {
        return  pref.getInt(monthlyCountKey, 0)
    }

    override fun saveCount(dailyCount:Int?,monthlyCount:Int?){
        pref.edit {
            if (dailyCount != null) {
                putInt(dailyCountKey, dailyCount)
            }
            if (monthlyCount != null) {
                putInt(monthlyCountKey,monthlyCount)
            }
        }
    }

    override fun clearCountOfTheDay(){
        pref.edit {
            putInt(dailyCountKey,0)
                .commit()
        }
    }
    override fun clearCountOfTheMonth(){
        pref.edit {
            putInt(monthlyCountKey,0)
                .commit()
        }
    }

    override fun isUseDemoData(): Boolean {
        return pref.getBoolean(demoDataKey,true)
    }

    companion object{
        val dailyGoalKey = "dailyGoal"
        val monthlyGoalKey ="monthlyGoal"
        val dailyCountKey = "dailyCount"
        val monthlyCountKey = "monthlyCount"
        val demoDataKey = "demoData"
    }
}