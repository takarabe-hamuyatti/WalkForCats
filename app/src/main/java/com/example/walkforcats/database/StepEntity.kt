package com.example.walkforcats.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*


@Entity
data class StepEntity(
     @PrimaryKey
     val date: String,
     var stepcount :Int,
)





