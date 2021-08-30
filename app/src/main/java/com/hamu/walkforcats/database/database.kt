package com.hamu.walkforcats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [monthlyInfo::class], version = 1,exportSchema = false)
abstract class monthlyInfoDatabase: RoomDatabase() {
    abstract val aboutMonthlyInfoDao: aboutMonthlyInfoDao
}

private lateinit var INSTANCE: monthlyInfoDatabase

fun getDatabase(context: Context): monthlyInfoDatabase {
    synchronized(monthlyInfoDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                monthlyInfoDatabase::class.java,
                "monthlyInfo").build()
        }
    }
    return INSTANCE
}

