package com.example.walkforcats.worker

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.walkforcats.database.StepEntity
import com.example.walkforcats.database.getDatabase
import com.example.walkforcats.repository.StepSaveRepository
import java.time.LocalDate

class savingWorker (appContext: Context,workerPrams:WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){

    companion object {
        const val WORK_NAME = "SavinginDaysEnd"
    }

    @SuppressLint("NewApi")
    override suspend fun doWork(): Result {

         try{
             val repository = StepSaveRepository(getDatabase(applicationContext))
             val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
             val todayCount = pref.getInt("todayCount",0)

             val newStepEntity = StepEntity(date = LocalDate.now().toString(),stepcount = todayCount)
             repository.saveStep(newStepEntity)

             pref.edit {
                 clear()
             }
             return Result.retry()
         }catch (e:Exception){
             return  Result.retry()
         }
    }
}