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

        //1日の終わりに実行します。
        val repeatingRequest = PeriodicWorkRequestBuilder<SavingMonthlyInfoWorker>(
            1,
            TimeUnit.DAYS,//24時間感覚で、
            5,
            TimeUnit.MINUTES//最後の5分を指定
        )
            //flex Time Interval　で毎月の最後が指定できなかったので、毎日0時に日付を確認して月が変わったかどうかを確認します。
            //flex Time Interval が単位時間の最後しか指定できないで、日付の最後の5分から10分遅らせる方法で日付を獲得します。
            .setInitialDelay(10,TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            SavingMonthlyInfoWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
}

