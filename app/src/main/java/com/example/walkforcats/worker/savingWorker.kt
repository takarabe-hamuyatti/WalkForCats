package com.example.walkforcats.worker

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class savingWorker (appContext: Context,workerPrams:WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){

    companion object {
        const val WORK_NAME = "SavinginDaysEnd"
    }

    override suspend fun doWork(): Result {

        val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        var todayCount = pref.getInt("todayCount",0)
        var weeklyCount = pref.getInt("weeklyCount",0)

         try{
             // todo roomの保存処理を書くよてい　


             pref.edit {
                 clear()
             }
             return Result.retry()
         }catch (e:Exception){
             return  Result.retry()
         }
    }
}