package com.hamu.walkforcats.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.DaylyStep
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.repository.StepSaveRepository
import java.time.LocalDate

class SavingDayCountWorker (appContext: Context, workerPrams:WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){

    companion object {
        const val WORK_NAME = "savingDaysEnd"
    }

    @SuppressLint("NewApi")
    override suspend fun doWork(): Result {

        // その日ごとの歩数を保存し、リセットしています。
         try{
             val repository = StepSaveRepository(getDatabase(applicationContext))
             val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
             val todayCount = pref.getInt("todayCount",0)

             val newStepEntity = DaylyStep(date = LocalDate.now().toString(),stepcount = todayCount)
             repository.saveStep(newStepEntity)

             pref.edit {
                 putInt("todayCount",0)
             }

             return Result.success()
         }catch (e:Exception){
             return  Result.retry()
         }
    }
}