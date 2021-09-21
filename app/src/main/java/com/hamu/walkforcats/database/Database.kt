package com.hamu.walkforcats.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamu.walkforcats.entity.MonthlyInfo

@Database(entities = [MonthlyInfo::class], version = 1,exportSchema = false)
abstract class MonthlyInfoDatabase: RoomDatabase() {
    abstract val aboutMonthlyInfoDao: AboutMonthlyInfoDao
}
