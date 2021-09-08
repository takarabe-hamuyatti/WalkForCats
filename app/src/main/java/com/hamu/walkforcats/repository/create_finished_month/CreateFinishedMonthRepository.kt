package com.hamu.walkforcats.repository.create_finished_month

import com.hamu.walkforcats.database.monthlyInfo


interface CreateFinishedMonthRepository {
    fun createFinishedMonth(monthlyInfo: monthlyInfo)
}

