package com.hamu.walkforcats.repository

import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.database.monthlyInfoDatabase

class UpdateExistingMonthRepository (private val database: monthlyInfoDatabase) {
    fun updateExistingMonth(monthlyInfo: monthlyInfo){
        database.aboutMonthlyInfoDao.update(monthlyInfo)
    }
}