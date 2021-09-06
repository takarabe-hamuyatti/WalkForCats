package com.hamu.walkforcats.repository

import com.hamu.walkforcats.database.monthlyInfo


interface CreateFinishedMonthRepository {
    fun createFinishedMonth(monthlyInfo: monthlyInfo)
}

