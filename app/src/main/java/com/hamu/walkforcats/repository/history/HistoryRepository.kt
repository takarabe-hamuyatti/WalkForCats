package com.hamu.walkforcats.repository.history

import com.hamu.walkforcats.database.MonthlyInfo
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    val allMonthlyInfo: Flow<List<MonthlyInfo>>

    suspend fun insertDemoData()

    suspend fun deleteDemoData()
}