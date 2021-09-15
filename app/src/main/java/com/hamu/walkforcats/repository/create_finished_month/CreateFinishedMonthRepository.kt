package com.hamu.walkforcats.repository.create_finished_month

import com.hamu.walkforcats.database.MonthlyInfo


interface CreateFinishedMonthRepository {
    fun createFinishedMonth(monthlyInfo: MonthlyInfo)
}

