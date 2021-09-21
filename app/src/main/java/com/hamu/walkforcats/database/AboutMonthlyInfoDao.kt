package com.hamu.walkforcats.database

import androidx.room.*
import com.hamu.walkforcats.entity.MonthlyInfo
import kotlinx.coroutines.flow.Flow


@Dao
interface AboutMonthlyInfoDao {
    @Query("select * from MonthlyInfo ORDER BY yearMonth DESC")
    fun getMonthlyInfo(): Flow<List<MonthlyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(monthlyInfo: MonthlyInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDemoData(monthlyInfos: Array<MonthlyInfo>)

    @Delete
    suspend fun deleteDemoData(monthlyInfos: Array<MonthlyInfo>)

}
