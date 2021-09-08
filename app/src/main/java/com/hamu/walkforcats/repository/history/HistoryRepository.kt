package com.hamu.walkforcats.repository.history

import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.monthlyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface HistoryRepository {
    val allMonthlyInfo: Flow<List<monthlyInfo>>

    suspend fun insertDemoData()

    suspend fun deleteDemoData()
}