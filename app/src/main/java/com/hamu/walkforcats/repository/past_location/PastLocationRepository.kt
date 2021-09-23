package com.hamu.walkforcats.repository.past_location

import com.hamu.walkforcats.entity.PastLocation
import kotlinx.coroutines.flow.Flow

interface PastLocationRepository {
    val PastLocationInfo :Flow<PastLocation>

    suspend fun updatePastLocation(longitude:Double, latitude:Double)
}