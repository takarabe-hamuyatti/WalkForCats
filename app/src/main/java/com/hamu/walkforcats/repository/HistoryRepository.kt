package com.hamu.walkforcats.repository

import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.database.monthlyInfoDatabase

class HistoryRepository (private val database: monthlyInfoDatabase){
    fun getMonthlyInfo():List<monthlyInfo>{
        return database.aboutMonthlyInfoDao.getMonthlyInfo()
    }
}