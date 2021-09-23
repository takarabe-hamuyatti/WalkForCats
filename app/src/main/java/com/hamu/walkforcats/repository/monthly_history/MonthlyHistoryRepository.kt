package com.hamu.walkforcats.repository.monthly_history

import com.hamu.walkforcats.entity.MonthlyInfo
import kotlinx.coroutines.flow.Flow

interface MonthlyHistoryRepository {
    val allMonthlyInfo: Flow<List<MonthlyInfo>>

    suspend fun insertDemoData()

    suspend fun deleteDemoData()
}