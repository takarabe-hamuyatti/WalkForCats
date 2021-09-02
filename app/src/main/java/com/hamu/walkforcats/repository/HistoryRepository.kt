package com.hamu.walkforcats.repository

import com.hamu.walkforcats.database.aboutMonthlyInfoDao

class HistoryRepository (Dao:aboutMonthlyInfoDao){
    val allMonthlyInfo = Dao.getMonthlyInfo()
}