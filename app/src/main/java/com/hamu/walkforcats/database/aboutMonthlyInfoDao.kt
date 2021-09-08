package com.hamu.walkforcats.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface aboutMonthlyInfoDao {
    @Query("select * from monthlyInfo ORDER BY yearMonth DESC")
    fun getMonthlyInfo(): Flow<List<monthlyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(monthlyInfo: monthlyInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDemoData(monthlyInfos: Array<monthlyInfo>)

    @Delete
    fun deleteDemoData(monthlyInfos: Array<monthlyInfo>)

}
