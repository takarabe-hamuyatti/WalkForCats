package com.hamu.walkforcats.repository


import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.monthlyInfo

class CreateFinishedMonthRepository {
       fun createFinishedMonth(Dao: aboutMonthlyInfoDao, monthlyInfo:monthlyInfo){
        Dao.insert(monthlyInfo)
    }
}

