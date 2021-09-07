package com.hamu.walkforcats.worker

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.hilt.work.HiltWorker
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.HistoryRepository
import com.hamu.walkforcats.repository.PreferenceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SavingMonthlyInfoWorker (
    appContext: Context,
    workerPrams:WorkerParameters,
    private val createFinishedMonthRepository: CreateFinishedMonthRepository,
    private val preferenceRepository: PreferenceRepository, )
    : CoroutineWorker(appContext,workerPrams){
    companion object {
        const val WORK_NAME = "resetDailyCountInDaysEnd"
    }

    override suspend fun doWork(): Result {
        // その日ごとの歩数をリセットしています。
         try{
             preferenceRepository.clearCountOfTheDay()//その日の歩数をリセットします。
             //1日の最後の5分を指定して、遅延させて日付を越えさせています。
             // 日付変更前、つまり当日中に保存する仕組みにすると端末の影響で遅延した時に登録する日付がずれてしまうと考えたためです。
             Handler().postDelayed({
                 Log.i("work","dowork")

                 val dt = LocalDate.now()
                 //日付を確認して、月が変わったかどうかを確認しています。
                 val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

                 if(isTheBeginningOfTheMonth){
                     //月が変わっていたら、これまでの月の記録を行っているので現時点から月を一つ減らした値で登録します。
                     val yearMonth = formattingYearMonth(dt)
                     //これまでの目標値、歩数、達成率を取得します。
                     val monthlyStepCount = preferenceRepository.getMonthlyCount()
                         val monthlyGoal = preferenceRepository.getMonthlyGoal()
                     val percent = truncating(getRatio(monthlyStepCount,monthlyGoal))

                     val finishedMonthlyInfo =
                         monthlyInfo(
                             yearMonth = yearMonth,
                             stepCount = monthlyStepCount,
                             goalOfMonth = monthlyGoal,
                             percentOfMonthlyGoal = percent
                         )
                     createFinishedMonthRepository.createFinishedMonth(monthlyInfo = finishedMonthlyInfo)
                     preferenceRepository.clearCountOfTheMonth()//月の歩数をリセットします。
                 }
              }, 400000)

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

    private fun formattingYearMonth(dt:LocalDate): Int {
        val beforeFormatting = dt.minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("YYYYMM")
        return beforeFormatting.format(formatter).toInt()
    }

}