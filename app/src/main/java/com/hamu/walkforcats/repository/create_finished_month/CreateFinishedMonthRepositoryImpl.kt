package com.hamu.walkforcats.repository.create_finished_month

import com.hamu.walkforcats.database.AboutMonthlyInfoDao
import com.hamu.walkforcats.entity.MonthlyInfo

class CreateFinishedMonthRepositoryImpl(
    private val dao: AboutMonthlyInfoDao
) : CreateFinishedMonthRepository {
    override fun createFinishedMonth(monthlyInfo: MonthlyInfo){
        dao.insert(monthlyInfo)
    }
}