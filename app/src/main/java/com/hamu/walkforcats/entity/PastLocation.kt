package com.hamu.walkforcats.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PastLocation(
    @PrimaryKey
    val primaryKey :Int,
    var longitude :Double,
    var latitude :Double
)