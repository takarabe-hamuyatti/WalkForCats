package com.hamu.walkforcats.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.hamu.walkforcats.entity.PastLocation

@Dao
 interface AboutPastLocationDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertNewLocation(pastLocation: PastLocation)
}