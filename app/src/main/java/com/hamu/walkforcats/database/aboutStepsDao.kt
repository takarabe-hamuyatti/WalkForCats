package com.hamu.walkforcats.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface aboutStepsDao {
    @Query("select * from DaylyStep")
    fun getSteops(): List<DaylyStep>

    @Insert
    fun insert(daylyStep: DaylyStep)
}