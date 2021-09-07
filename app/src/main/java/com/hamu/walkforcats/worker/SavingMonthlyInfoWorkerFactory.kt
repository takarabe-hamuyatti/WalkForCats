package com.hamu.walkforcats.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.hamu.walkforcats.repository.CreateFinishedMonthRepository
import com.hamu.walkforcats.repository.PreferenceRepository
import javax.inject.Inject

class SavingMonthlyInfoWorkerFactory @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val createFinishedMonthRepository: CreateFinishedMonthRepository
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return  SavingMonthlyInfoWorker(appContext,workerParameters,createFinishedMonthRepository,preferenceRepository)
    }
}