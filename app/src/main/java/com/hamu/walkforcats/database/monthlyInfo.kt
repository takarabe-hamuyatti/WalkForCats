package com.hamu.walkforcats.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.YearMonth

@Entity
data class monthlyInfo(
     @PrimaryKey
     val yearMonth: Int,
     val stepCount: Int,
     val goalOfMonth: Int?,
     val percentOfMonthlyGoal:Float?,
)







