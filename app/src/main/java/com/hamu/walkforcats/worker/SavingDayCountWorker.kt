package com.hamu.walkforcats.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.preferenceRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

class SavingDayCountWorker (appContext: Context, workerPrams:WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){

    val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    val repository : preferenceRepository = preferenceRepository(pref)

    companion object {
        const val WORK_NAME = "resetDailyCountInDaysEnd"
    }

    @SuppressLint("NewApi")
    override suspend fun doWork(): Result {
        // その日ごとの歩数をリセットしています。
         try{
             val dt = LocalDate.now()
             val whetherTheBeginningOfTheMonth = dt.dayOfMonth == 1

             val monthlyStepCount = repository.getMonthlyCountFromPreference()
             val monthlyGoal = repository.getMonthlyGoalFromPreference()

             val thisMonth = dt.monthValue
             val lastMonth = dt.monthValue - 1
             if(!whetherTheBeginningOfTheMonth){
                 var monthlyInfo =
                     monthlyInfo(
                         yearMonth = YearMonth.now(),
                         stepCount = monthlyStepCount,
                         goalOfMonth = monthlyGoal
                     )
                 }
             }
             YearMonth.

             if(whetherTheBeginningOfTheMonth){
                 var monthlyInfo = monthlyInfo(yearMonth = )
             }
             repository.clearCountOfTheDay()
             return Result.success()
         }catch (e:Exception){
             return  Result.retry()
         }
    }
}