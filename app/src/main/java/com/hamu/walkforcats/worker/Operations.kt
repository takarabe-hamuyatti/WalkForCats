package com.hamu.walkforcats.worker

import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class Operations:Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {


        //todo 単純な保存でも例外が起こりうるケースを考え、制約を設定する。
       /* val constraints = Constraints.Builder()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        */

        //1日の終わりに実行します。
        val repeatingRequest = PeriodicWorkRequestBuilder<SavingDayCountWorker>(
            1,
            TimeUnit.DAYS,//24時間感覚で、
            5,
            TimeUnit.MINUTES//最後の5分を指定
                 )
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SavingDayCountWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
}
