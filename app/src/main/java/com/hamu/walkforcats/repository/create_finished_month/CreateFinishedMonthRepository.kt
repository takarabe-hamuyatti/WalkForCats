package com.hamu.walkforcats.repository.create_finished_month

import com.hamu.walkforcats.entity.MonthlyInfo


interface CreateFinishedMonthRepository {
    suspend fun createFinishedMonth(monthlyInfo: MonthlyInfo)
}

