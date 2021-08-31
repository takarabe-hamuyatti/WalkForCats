package com.hamu.walkforcats.repository


import com.hamu.walkforcats.database.monthlyInfoDatabase
import com.hamu.walkforcats.database.monthlyInfo

class CreateFinishedMonthRepository (private val database: monthlyInfoDatabase){
    fun createFinishedMonth(monthlyInfo:monthlyInfo){
        database.aboutMonthlyInfoDao.insert(monthlyInfo)
    }
}

