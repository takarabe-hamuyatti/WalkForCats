package com.hamu.walkforcats.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.MonthlyInfo
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.utils.formattingYearMonth
import com.hamu.walkforcats.utils.getRatio
import com.hamu.walkforcats.utils.truncating
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@HiltWorker
class EveryDayRecurringWorker @AssistedInject constructor(
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
             processingContentOfWork()
             return Result.success()
         }catch (e:Exception){
             return  Result.retry()
         }
    }

    private fun processingContentOfWork(){
        val dt = LocalDate.now()
        //日付を確認して、月が変わったかどうかを確認しています。
        val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

        if (isTheBeginningOfTheMonth) {
            val yearMonth = formattingYearMonth(dt)
            //これまでの目標値、歩数、達成率を取得します。
            val monthlyStepCount = preferenceRepository.getMonthlyCount()
            val monthlyGoal = preferenceRepository.getMonthlyGoal()
            val percent = truncating(getRatio(monthlyStepCount.toInt(), monthlyGoal.toInt())).toString()

            val finishedMonthlyInfo =
                MonthlyInfo(
                    id = null,
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
        //その日の歩数をリセットします。
        preferenceRepository.clearCountOfTheDay()
    }

}