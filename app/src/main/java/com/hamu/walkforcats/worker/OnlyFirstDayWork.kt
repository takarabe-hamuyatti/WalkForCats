package com.hamu.walkforcats.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.hamu.walkforcats.MyApplication
import com.hamu.walkforcats.database.MonthlyInfo
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.utils.changeToPercent
import com.hamu.walkforcats.utils.formattingYearMonth
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@HiltWorker
class OnlyFirstDayWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerPrams: WorkerParameters,
    private val createFinishedMonthRepository: CreateFinishedMonthRepository,
    private val preferenceRepository: PreferenceRepository
): CoroutineWorker(appContext,workerPrams) {

    override suspend fun doWork(): Result {
        try{
            setupRecurringWork()
            processingContentOfWork()
            return Result.success()
        }catch (e:Exception){
            return  Result.retry()
        }
    }

    private fun setupRecurringWork(){
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()
        val repeatingRequest =
            PeriodicWorkRequestBuilder<EveryDayRecurringWorker>(
                1,
                TimeUnit.DAYS,
                5,
                TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .addTag(MyApplication.WorkTag)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            EveryDayRecurringWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun processingContentOfWork(){
        val dt = LocalDate.now()
        //日付を確認して、月が変わったかどうかを確認しています。
        val isTheBeginningOfTheMonth = dt.dayOfMonth == 1

        if (isTheBeginningOfTheMonth) {
            val yearMonth = formattingYearMonth(dt)
            //これまでの目標値、歩数、達成率を取得します。
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
            //Roomを使って保存しています。
            createFinishedMonthRepository.createFinishedMonth(monthlyInfo = finishedMonthlyInfo)
            //月の歩数をリセットします。
            preferenceRepository.clearCountOfTheMonth()
        }
        //その日の歩数をリセットします。
        preferenceRepository.clearCountOfTheDay()
    }
}