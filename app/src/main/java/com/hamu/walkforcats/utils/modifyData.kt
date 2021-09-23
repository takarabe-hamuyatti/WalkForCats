package com.hamu.walkforcats.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun changeToPercent(count: Int?, goal: Int?):Float?{
    val tmp = count?.toFloat()?.div(goal!!)?.times(100)
    return tmp?.times(10)?.toInt()?.toFloat()?.div(10)
}

fun formattingYearMonth(dt: LocalDate): Int {
    //月が変わっていたら、それまでの月の記録を行っているので現時点から月を一つ減らした値で登録します。
    val beforeFormatting = dt.minusMonths(1)
    val formatter = DateTimeFormatter.ofPattern("yyyyMM")
    return beforeFormatting.format(formatter).toInt()
}