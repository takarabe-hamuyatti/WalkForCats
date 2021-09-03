package com.hamu.walkforcats.worker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.PreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SavingMonthlyInfoWorker (appContext: Context, workerPrams:WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){


    companion object {
        const val WORK_NAME = "resetDailyCountInDaysEnd"
    }

    @SuppressLint("NewApi", "WeekBasedYear")
    override suspend fun doWork(): Result {
        // その日ごとの歩数をリセットしています。
         try{
             //1日の最後の5分を指定して、遅延させて日付を越えさせています。
             // 日付変更前、つまり当日中に保存する仕組みにすると端末の影響で遅延した時に登録する日付がずれてしまうと考えたためです。
             Handler().postDelayed({
                 Log.i("work","dowork")
                 val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                 val repository= PreferenceRepository(pref)
                 repository.clearCountOfTheDay()//その日の歩数をリセットします。

                 val dt = LocalDate.now()
                 //日付を確認して、月が変わったかどうかを確認しています。
                 val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

                 if(isTheBeginningOfTheMonth){
                     //これまでの目標値、歩数、達成率を取得します。
                     val monthlyStepCount = repository.getMonthlyCount()
                     val monthlyGoal = repository.getMonthlyGoal()
                     val percent = truncating(getRatio(monthlyStepCount,monthlyGoal))
                     //月が変わっていたら、これまでの月の記録を行っているので現時点から月を一つ減らした値で登録します。
                     val beforeFormatting = dt.minusMonths(1)

                     val formatter = DateTimeFormatter.ofPattern("YYYYMM")
                     val yearMonth = beforeFormatting.format(formatter).toInt()

                     val finishedMonthlyInfo =
                         monthlyInfo(
                             yearMonth = yearMonth,
                             stepCount = monthlyStepCount,
                             goalOfMonth = monthlyGoal,
                             percentOfMonthlyGoal = percent
                         )
                     val Dao = getDatabase(applicationContext).aboutMonthlyInfoDao
                     val createFinishedMonthRepository = CreateFinishedMonthRepository()
                     createFinishedMonthRepository.createFinishedMonth(Dao,monthlyInfo = finishedMonthlyInfo)

                     repository.clearCountOfTheMonth()//最後に、月の歩数もリセットします。

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
}