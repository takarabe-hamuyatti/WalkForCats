package com.hamu.walkforcats.database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DaylyStep(
     @PrimaryKey
     val date: String,
     var stepcount :Int,
)






