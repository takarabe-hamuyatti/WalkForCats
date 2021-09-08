package com.hamu.walkforcats

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.hamu.walkforcats.worker.SavingMonthlyInfoWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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
        delayedInit()
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }
    private fun setupRecurringWork() {
        Log.i("work", "initwork")
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