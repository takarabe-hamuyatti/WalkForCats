package com.hamu.walkforcats.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [monthlyInfo::class], version = 1,exportSchema = false)
abstract class monthlyInfoDatabase: RoomDatabase() {
    abstract val aboutMonthlyInfoDao: aboutMonthlyInfoDao
}

@Database(entities = [isChangeOfDate::class],version = 1,exportSchema = false)
abstract  class  checkIsChangeOfDateDatabase:RoomDatabase(){
    abstract  val aboutIsChangeOfDate:aboutIsChangeOfDateDao
}
