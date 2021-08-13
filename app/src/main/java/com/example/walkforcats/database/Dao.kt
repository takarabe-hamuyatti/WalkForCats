package com.example.walkforcats.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
   /* @Query("select * from ")
    fun getVideos(): LiveData<List<>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<>)*/
}