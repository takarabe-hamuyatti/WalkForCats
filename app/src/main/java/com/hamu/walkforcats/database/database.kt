package com.hamu.walkforcats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DaylyStep::class], version = 1,exportSchema = false)
abstract class StepDatabase: RoomDatabase() {
    abstract val aboutStepsDao: aboutStepsDao
}

private lateinit var INSTANCE: StepDatabase

fun getDatabase(context: Context): StepDatabase {
    synchronized(StepDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                StepDatabase::class.java,
                "Steps").build()
        }
    }
    return INSTANCE
}
