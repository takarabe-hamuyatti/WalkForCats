package com.example.walkforcats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StepEntity::class], version = 1)
abstract class StepDatabase: RoomDatabase() {
    abstract val Dao: Dao
}

private lateinit var INSTANCE: StepDatabase

fun getDatabase(context: Context): StepDatabase {
    synchronized(StepDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                StepDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}
