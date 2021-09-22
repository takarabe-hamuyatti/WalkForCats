package com.hamu.walkforcats.repository.past_location

import com.hamu.walkforcats.database.dao.AboutPastLocationDao
import com.hamu.walkforcats.entity.PastLocation
import kotlinx.coroutines.flow.Flow

class PastLocationRepositoryImpl(
    private val dao: AboutPastLocationDao
):PastLocationRepository {
    override val PastLocationInfo: Flow<PastLocation>
             get() = dao.getPastLocation()
}