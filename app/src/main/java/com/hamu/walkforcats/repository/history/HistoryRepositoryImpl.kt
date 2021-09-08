package com.hamu.walkforcats.repository.history

import com.hamu.walkforcats.database.aboutMonthlyInfoDao
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.repository.history.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HistoryRepositoryImpl(
    private val dao:aboutMonthlyInfoDao
) : HistoryRepository {
    override val allMonthlyInfo: Flow<List<monthlyInfo>>
        get() = dao.getMonthlyInfo()

    val demoMonth1 =  monthlyInfo(200001,20000,200000,10f)
    val demoMonth2 =  monthlyInfo(199906,30000,90000,30f)
    val demoMonth3 =  monthlyInfo(188905,40000,80000,50f)
    val demoMonth4=  monthlyInfo(154205,80000,100000,80f)
    val demoMonth5 =  monthlyInfo(120010,100000,100000,100f)

    val demoMonthlyInfo :Array<monthlyInfo> = arrayOf(demoMonth1,demoMonth2,demoMonth3,demoMonth4,demoMonth5)

    override suspend fun insertDemoData() {
        withContext(Dispatchers.IO) {
            dao.insertDemoData(demoMonthlyInfo)
        }
    }

    override suspend fun deleteDemoData(){
        withContext(Dispatchers.IO) {
            dao.deleteDemoData(demoMonthlyInfo)
        }
    }
}