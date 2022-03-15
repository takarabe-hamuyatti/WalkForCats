package com.hamu.walkforcats.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.entity.MonthlyInfo
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.utils.changeToPercent
import com.hamu.walkforcats.utils.formattingYearMonth
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

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
         try{
             processingContentOfWork()
             return Result.success()
         }catch (e:Exception){
             return  Result.retry()
         }
    }

    private suspend fun processingContentOfWork(){
        val dt = LocalDate.now()
        //日付を確認して、月が変わったかどうかを確認しています。
        val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

        if (isTheBeginningOfTheMonth) {
            val yearMonth = formattingYearMonth(dt)
            val monthlyStepCount = preferenceRepository.getMonthlyCount().toString()
            val monthlyGoal = preferenceRepository.getMonthlyGoal().toString()
            val percent = changeToPercent(monthlyStepCount.toInt(),monthlyGoal.toInt()).toString()

            val finishedMonthlyInfo =
                MonthlyInfo(
                    id = null,
                    yearMonth = yearMonth,
                    stepCount = monthlyStepCount,
                    monthlyGoal = monthlyGoal,
                    monthlyPercent = percent
                )
            createFinishedMonthRepository.createFinishedMonth(monthlyInfo = finishedMonthlyInfo)
            preferenceRepository.clearCountOfTheMonth()
        }
        preferenceRepository.clearCountOfTheDay()
    }

}