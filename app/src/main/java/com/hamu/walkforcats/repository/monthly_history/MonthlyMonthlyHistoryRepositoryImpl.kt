package com.hamu.walkforcats.repository.monthly_history

import com.hamu.walkforcats.database.dao.AboutMonthlyInfoDao
import com.hamu.walkforcats.entity.MonthlyInfo
import kotlinx.coroutines.flow.Flow

class MonthlyMonthlyHistoryRepositoryImpl(
    private val dao: AboutMonthlyInfoDao
) : MonthlyHistoryRepository {
    override val allMonthlyInfo: Flow<List<MonthlyInfo>>
        get() = dao.getMonthlyInfo()

    //モックです。使い始めで履歴がないと寂しいので設置しました。設定から有効にするか変更できます
    val demoMonth1 =  MonthlyInfo(id = 1,200001,"20000","200000","10")
    val demoMonth2 =  MonthlyInfo(id = 2,199906,"30000","90000","30")
    val demoMonth3 =  MonthlyInfo(id = 3,188905,"60000","100000","60")
    val demoMonth4=  MonthlyInfo(id = 4,154205,"80000","100000","80")
    val demoMonth5 =  MonthlyInfo(id = 5,120010,"100000","100000","100")

    val demoMonthlyInfo :Array<MonthlyInfo> = arrayOf(demoMonth1,demoMonth2,demoMonth3,demoMonth4,demoMonth5)

    override suspend fun insertDemoData() {
        dao.insertDemoData(demoMonthlyInfo)
    }

    override suspend fun deleteDemoData(){
        dao.deleteDemoData(demoMonthlyInfo)
    }
}