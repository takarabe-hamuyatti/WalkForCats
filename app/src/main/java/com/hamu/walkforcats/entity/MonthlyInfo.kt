package com.hamu.walkforcats.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MonthlyInfo(
     @PrimaryKey(autoGenerate = true)
     val id :Int?,
     val yearMonth: Int,
     val stepCount: String,
     val monthlyGoal: String,
     val monthlyPercent:String,
)







