package com.example.walkforcats.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class savingWorker (appContext: Context,workerPrams:WorkerParameters)
    : Worker(appContext,workerPrams){

    override fun doWork(): Result {


        return Result.retry()
    }
}