package com.hamu.walkforcats.repository.history

import com.hamu.walkforcats.entity.MonthlyInfo
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    val allMonthlyInfo: Flow<List<MonthlyInfo>>

    suspend fun insertDemoData()

    suspend fun deleteDemoData()
}