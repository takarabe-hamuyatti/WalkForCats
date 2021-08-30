package com.hamu.walkforcats.repository


import com.hamu.walkforcats.database.monthlyInfoDatabase
import com.hamu.walkforcats.database.monthlyInfo

class CreateNewMonthRepository (private val database: monthlyInfoDatabase){
    fun createNewMonth(monthlyInfo:monthlyInfo){
        database.aboutMonthlyInfoDao.insert(monthlyInfo)
    }
}

