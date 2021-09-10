package com.hamu.walkforcats.utils

fun getRatio(num1: Int?, num2: Int?): Float? {
    return num1?.toFloat()?.div(num2!!)?.times(100)
}

fun truncating(num:Float?):Float?{
    return num?.times(10)?.toInt()?.toFloat()?.div(10)
}