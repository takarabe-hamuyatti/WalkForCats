package com.hamu.walkforcats.repository.create_finished_month

import com.hamu.walkforcats.database.dao.AboutMonthlyInfoDao
import com.hamu.walkforcats.entity.MonthlyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class CreateFinishedMonthRepositoryImpl(
    private val dao: AboutMonthlyInfoDao
) : CreateFinishedMonthRepository {
    override suspend fun createFinishedMonth(monthlyInfo: MonthlyInfo) {
        coroutineScope {
            withContext(Dispatchers.IO) {
                dao.insert(monthlyInfo)
            }
        }
    }
}