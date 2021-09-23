package com.hamu.walkforcats.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamu.walkforcats.entity.PastLocation
import kotlinx.coroutines.flow.Flow

@Dao
 interface AboutPastLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewLocation(pastLocation: PastLocation)

    @Query("select * From PastLocation ")
    fun getPastLocation(): Flow<PastLocation>
}