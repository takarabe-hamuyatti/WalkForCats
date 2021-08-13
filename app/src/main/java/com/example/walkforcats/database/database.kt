package com.example.walkforcats.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
    abstract val Dao: Dao
}

private lateinit var INSTANCE: VideosDatabase

fun getDatabase(context: Context): VideosDatabase {
    synchronized(VideosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                VideosDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}
