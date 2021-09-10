package com.hamu.walkforcats.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.hamu.walkforcats.MyApplication
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.create_finished_month.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.utils.getRatio
import com.hamu.walkforcats.utils.truncating
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class OnlyFirstDayWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerPrams: WorkerParameters,
    private val createFinishedMonthRepository: CreateFinishedMonthRepository,
    private val preferenceRepository: PreferenceRepository
): CoroutineWorker(appContext,workerPrams) {

    override suspend fun doWork(): Result {
        try{
            Timber.i("StartWorkMnager")
            setupRecurringWork()

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
            //その日の歩数をリセットします。
            preferenceRepository.clearCountOfTheDay()
            return Result.success()
        }catch (e:Exception){
            return  Result.retry()
        }
    }

    private fun formattingYearMonth(dt: LocalDate): Int {
        //月が変わっていたら、これまでの月の記録を行っているので現時点から月を一つ減らした値で登録します。
        val beforeFormatting = dt.minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("YYYYMM")
        return beforeFormatting.format(formatter).toInt()
    }

    fun setupRecurringWork(){
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()
        val repeatingRequest =
            PeriodicWorkRequestBuilder<SavingMonthlyInfoWorker>(
                1,
                TimeUnit.DAYS,
                5,
                TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .addTag(MyApplication.WorkTag)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            SavingMonthlyInfoWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )

    }

}