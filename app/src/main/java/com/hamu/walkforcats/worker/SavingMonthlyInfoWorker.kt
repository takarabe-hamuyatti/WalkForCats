package com.hamu.walkforcats.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.PreferenceRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SavingMonthlyInfoWorker (appContext: Context, workerPrams:WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){

    val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    val PreferenceRepository : PreferenceRepository = PreferenceRepository(pref)

    val database = getDatabase(applicationContext)

    @SuppressLint("NewApi", "WeekBasedYear")
    override suspend fun doWork(): Result {
        // その日ごとの歩数をリセットしています。
         try{
             PreferenceRepository.clearCountOfTheDay()

             val monthlyStepCount = PreferenceRepository.getMonthlyCountFromPreference()
             val monthlyGoal = PreferenceRepository.getMonthlyGoalFromPreference()
             val percent = truncating(getRatio(monthlyStepCount,monthlyGoal))

             val dt = LocalDate.now()
             //日付を確認して、月が変わったかどうかを確認しています。
             val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

             //月が変わったかどうかに応じて更新する対象の月を変更しています。
             val beforeFormatting = dt.minusMonths(1)

             val formatter = DateTimeFormatter.ofPattern("YYYY-MM")
             val yearMonth = beforeFormatting.format(formatter).toInt()

             if(isTheBeginningOfTheMonth){
                 val finishedMonthlyInfo =
                     monthlyInfo(
                         yearMonth = yearMonth,
                         stepCount = monthlyStepCount,
                         goalOfMonth = monthlyGoal,
                         percentOfMonthlyGoal = percent
                     )

                 val createFinishedMonthRepository = CreateFinishedMonthRepository(database)
                 createFinishedMonthRepository.createFinishedMonth(monthlyInfo = finishedMonthlyInfo)
             }

             return Result.success()
         }catch (e:Exception){
             return  Result.retry()
         }
    }

    private fun getRatio(num1: Int?, num2: Int?): Float? {
        return num1?.toFloat()?.div(num2!!)?.times(100)
    }

    private fun truncating(num:Float?):Float?{
        return num?.times(10)?.toInt()?.toFloat()?.div(10)
    }

    companion object {
        const val WORK_NAME = "resetDailyCountInDaysEnd"
    }
}