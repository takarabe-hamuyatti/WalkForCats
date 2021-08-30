package com.hamu.walkforcats.worker

/*
import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hamu.walkforcats.database.daylyStep
import com.hamu.walkforcats.database.getDatabase
import com.hamu.walkforcats.repository.StepSaveRepository
import java.time.LocalDate

class SavingMonthlyCountWorker (appContext: Context, workerPrams: WorkerParameters)
    : CoroutineWorker(appContext,workerPrams){

    companion object {
        const val WORK_NAME = "savinginWeeklyEnd"
    }

    @SuppressLint("NewApi")
    override suspend fun doWork(): Result {

        try{
            //毎日日付、月を確認して月が変わったかどうか確かめる
            val repository = StepSaveRepository(getDatabase(applicationContext))
            val pref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

            //val newStep = daylyStep(date = LocalDate.now().toString(),stepCount = todayCount)

            return Result.retry()
        }catch (e:Exception){
            return  Result.retry()
        }
    }
}

 */