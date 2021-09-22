package com.hamu.walkforcats.repository.past_location

import com.hamu.walkforcats.database.dao.AboutPastLocationDao
import com.hamu.walkforcats.entity.PastLocation
import com.hamu.walkforcats.utils.UniqueId.Companion.PAST_LOCATION_KEY
import kotlinx.coroutines.flow.Flow

class PastLocationRepositoryImpl(
    private val dao: AboutPastLocationDao
):PastLocationRepository {
    override val PastLocationInfo: Flow<PastLocation>
             get() = dao.getPastLocation()

    override suspend fun updatePastLocation(longitude:Double, latitude:Double) {
        val newPastLocation = PastLocation(PAST_LOCATION_KEY,longitude,latitude)
        dao.insertNewLocation(newPastLocation)
    }
}