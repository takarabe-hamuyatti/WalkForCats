package com.hamu.walkforcats.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface aboutMonthlyInfoDao {
    @Query("select * from monthlyInfo ORDER BY yearMonth DESC")
    fun getMonthlyInfo(): Flow<List<monthlyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(monthlyInfo: monthlyInfo)

}

