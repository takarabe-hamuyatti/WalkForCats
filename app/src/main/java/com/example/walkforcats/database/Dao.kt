package com.example.walkforcats.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("select * from StepEntity")
    fun getSteops(): List<StepEntity>

    @Insert
    fun insert(stepEntity: StepEntity)
}