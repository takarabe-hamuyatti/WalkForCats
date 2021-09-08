package com.hamu.walkforcats.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@HiltWorker
class SavingMonthlyInfoWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerPrams:WorkerParameters,
    private val createFinishedMonthRepository: CreateFinishedMonthRepository,
    private val preferenceRepository: PreferenceRepository
    )
    : CoroutineWorker(appContext,workerPrams){
    companion object {
        const val WORK_NAME = "resetDailyCountInDaysEnd"
    }

    override suspend fun doWork(): Result {
        // その日ごとの歩数をリセットしています。
         try{
             Timber.i("StartWorkMnager")
             preferenceRepository.clearCountOfTheDay()//その日の歩数をリセットします。
             //1日の最後の5分を指定して、遅延させて日付を越えさせています。
             // 日付変更前、つまり当日中に保存する仕組みにすると端末の影響で遅延した時に登録する日付がずれてしまうと考えたためです。

             GlobalScope.launch {
                 delay(300000)
                 Timber.i("delay")

                 val dt = LocalDate.now()
                 //日付を確認して、月が変わったかどうかを確認しています。
                 val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

                 if (isTheBeginningOfTheMonth) {
                     val yearMonth = formattingYearMonth(dt)
                     //これまでの目標値、歩数、達成率を取得します。
                     val monthlyStepCount = preferenceRepository.getMonthlyCount()
                     val monthlyGoal = preferenceRepository.getMonthlyGoal()
                     val percent = truncating(getRatio(monthlyStepCount, monthlyGoal))

                     val finishedMonthlyInfo =
                         monthlyInfo(
                             yearMonth = yearMonth,
                             stepCount = monthlyStepCount,
                             monthlyGoal = monthlyGoal,
                             monthlyPercent = percent
                         )
                     //Roomを使って保存しています。
                     createFinishedMonthRepository.createFinishedMonth(monthlyInfo = finishedMonthlyInfo)
                     //月の歩数をリセットします。
                     preferenceRepository.clearCountOfTheMonth()
                 }
             }

             return Result.success()
         }catch (e:Exception){
             Timber.i("WorkmanagerFailed")
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
        //月が変わっていたら、これまでの月の記録を行っているので現時点から月を一つ減らした値で登録します。
        val beforeFormatting = dt.minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("YYYYMM")
        return beforeFormatting.format(formatter).toInt()
    }

}