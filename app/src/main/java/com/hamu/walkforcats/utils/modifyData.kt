package com.hamu.walkforcats.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getRatio(num1: Int?, num2: Int?): Float? {
    return num1?.toFloat()?.div(num2!!)?.times(100)
}

fun truncating(num:Float?):Float?{
    return num?.times(10)?.toInt()?.toFloat()?.div(10)
}

fun formattingYearMonth(dt: LocalDate): Int {
    //月が変わっていたら、これまでの月の記録を行っているので現時点から月を一つ減らした値で登録します。
    val beforeFormatting = dt.minusMonths(1)
    val formatter = DateTimeFormatter.ofPattern("yyyyMM")
    return beforeFormatting.format(formatter).toInt()
}