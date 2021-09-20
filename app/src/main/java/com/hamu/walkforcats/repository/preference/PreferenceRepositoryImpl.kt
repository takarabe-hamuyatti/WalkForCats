package com.hamu.walkforcats.repository.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import com.hamu.walkforcats.utils.UniqueId.Companion.CHANGE_CAT_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.CHECK_FIRST_TIME_OF_HISTRY_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.CHECK_FIRST_TIME_OF_STEPCOUNT_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.DAILY_COUNT_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.DAILY_GOAL_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.DEMO_DATA_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.MONTHLY_COUNT_KEY
import com.hamu.walkforcats.utils.UniqueId.Companion.MONTHLY_GOAL_KEY


class PreferenceRepositoryImpl(
    private val pref:SharedPreferences
): PreferenceRepository {
    override fun getDailyGoal(): Int? {
        return pref.getString(DAILY_GOAL_KEY, "15000")?.toInt()
    }

    override fun getMonthlyGoal():Int? {
        return pref.getString(MONTHLY_GOAL_KEY, "50000")?.toInt()
    }

    override fun getDailyCount(): Int {
        return pref.getInt(DAILY_COUNT_KEY, 0)
    }

    override fun getMonthlyCount(): Int {
        return pref.getInt(MONTHLY_COUNT_KEY, 0)
    }

    override fun saveCount(dailyCount:Int?,monthlyCount:Int?){
        pref.edit {
            if (dailyCount != null) {
                putInt(DAILY_COUNT_KEY, dailyCount)
            }
            if (monthlyCount != null) {
                putInt(MONTHLY_COUNT_KEY,monthlyCount)
            }
        }
    }

    override fun clearCountOfTheDay(){
        pref.edit {
            putInt(DAILY_COUNT_KEY,0)
                .commit()
        }
    }
    override fun clearCountOfTheMonth(){
        pref.edit {
            putInt(MONTHLY_COUNT_KEY,0)
                .commit()
        }
    }

    override fun isUseDemoData(): Boolean {
        return pref.getBoolean(DEMO_DATA_KEY,true)
    }

    override fun isCangeCat(): Boolean {
        return pref.getBoolean(CHANGE_CAT_KEY,false)
    }

    override fun checkFirstTimeOfHistry():Boolean {
        return pref.getBoolean(CHECK_FIRST_TIME_OF_HISTRY_KEY,true)
    }

    override fun changeIsFirstTimeOfHistry() {
        pref.edit{
            putBoolean(CHECK_FIRST_TIME_OF_HISTRY_KEY,false)
        }
    }

    override fun checkFirstTimeOfStepCount(): Boolean {
        return pref.getBoolean(CHECK_FIRST_TIME_OF_STEPCOUNT_KEY,true)
    }

    override fun changeIsFirstTimeOfStepCount() {
        pref.edit{
            putBoolean(CHECK_FIRST_TIME_OF_STEPCOUNT_KEY,false)
        }
    }

}