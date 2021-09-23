package com.hamu.walkforcats.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamu.walkforcats.database.dao.AboutMonthlyInfoDao
import com.hamu.walkforcats.database.dao.AboutPastLocationDao
import com.hamu.walkforcats.entity.MonthlyInfo
import com.hamu.walkforcats.entity.PastLocation

@Database(entities = arrayOf(MonthlyInfo::class,PastLocation::class), version = 1,exportSchema = false)
abstract class AboutHistoryDatabase: RoomDatabase() {
    abstract val aboutMonthlyInfoDao: AboutMonthlyInfoDao
    abstract val aboutPastLocationDao: AboutPastLocationDao
}
