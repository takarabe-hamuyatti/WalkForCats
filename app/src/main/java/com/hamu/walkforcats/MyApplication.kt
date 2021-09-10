package com.hamu.walkforcats

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.hamu.walkforcats.worker.SavingMonthlyInfoWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import java.time.temporal.ChronoUnit


@HiltAndroidApp
class MyApplication :Application(),Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            val target = LocalTime.of(23,59)
            val minutes: Long = ChronoUnit.MINUTES.between(LocalTime.now(), target)
            //最後の5分をflextimeinterval に指定しているので、
            // 12:01　から12:06をその5分に入れて確実に保存するために23:59から7分遅延させています。
            setupRecurringWork(minutes+7)
        }
    }
    private fun setupRecurringWork(minute:Long) {
        Timber.i("initwork")
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
                .addTag(WorkTag)
                .setInitialDelay(minute,TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            SavingMonthlyInfoWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    companion object{
        val WorkTag = "everydayWork"
    }

}