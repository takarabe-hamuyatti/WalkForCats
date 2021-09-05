package com.hamu.walkforcats.repository

import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.monthlyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository (Dao:aboutMonthlyInfoDao){
    val dao = Dao
    val allMonthlyInfo = dao.getMonthlyInfo()

    val demoMonth1 =  monthlyInfo(200001,20000,200000,10f)
    val demoMonth2 =  monthlyInfo(199906,30000,90000,30f)
    val demoMonth3 =  monthlyInfo(188905,40000,80000,50f)
    val demoMonth4=  monthlyInfo(188905,80000,100000,80f)
    val demoMonth5 =  monthlyInfo(120010,100000,100000,100f)

    val demoMonthlyInfo :Array<monthlyInfo> = arrayOf(demoMonth1,demoMonth2,demoMonth3,demoMonth4,demoMonth5)

    suspend fun insertDemoData() {
        withContext(Dispatchers.IO) {
            dao.insertDemoData(demoMonthlyInfo)
        }
    }

    suspend fun deleteDemoData(){
        withContext(Dispatchers.IO) {
            dao.deleteDemoData(demoMonthlyInfo)
        }
    }
}