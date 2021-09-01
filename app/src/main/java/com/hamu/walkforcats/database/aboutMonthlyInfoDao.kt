package com.hamu.walkforcats.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface aboutMonthlyInfoDao {
    @Query("select * from monthlyInfo")
    suspend fun getMonthlyInfo(): List<monthlyInfo>

    @Insert
    suspend fun insert(monthlyInfo: monthlyInfo)

}

