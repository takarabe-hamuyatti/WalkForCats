package com.hamu.walkforcats.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.YearMonth

@Entity
data class daylyStep(
     @PrimaryKey
     val date: String,
     var stepCount :Int,
)

@Entity
data class monthlyStep(
     @PrimaryKey
     val yearMonth: YearMonth,
     val stepCount: Int,
)
//todo どの猫を獲得しているか保存？






