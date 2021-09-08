package com.hamu.walkforcats.repository.create_finished_month

import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.monthlyInfo

class CreateFinishedMonthRepositoryImpl(
    private val dao: aboutMonthlyInfoDao
) : CreateFinishedMonthRepository {
    override fun createFinishedMonth(monthlyInfo: monthlyInfo){
        dao.insert(monthlyInfo)
    }
}