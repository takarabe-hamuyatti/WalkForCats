package com.hamu.walkforcats.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object BindingAdapter {
    @BindingAdapter("setProgressWithAnimation")
    @JvmStatic
    fun setProgressWithAnimation(view: com.mikhaellopez.circularprogressbar.CircularProgressBar,count:String) {
        view.setProgressWithAnimation(count.toInt().toFloat())
    }
    @BindingAdapter("cpb_progress_max")
    @JvmStatic
    fun setProgressMax(view: com.mikhaellopez.circularprogressbar.CircularProgressBar,goal:Float){
        view.progressMax = goal

    }
    @BindingAdapter("changeToString")
    @JvmStatic
    fun changeToString(view:TextView,goal: Float) {
        var tmp = goal.toInt()
        view.text = tmp.toString()
    }
    @SuppressLint("NewApi")
    @BindingAdapter("formatYearandMonth")
    @JvmStatic
    fun formatYearandMonth(view:TextView,date: LocalDate){
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM")
        val yearMonth = date.format(formatter).toString()
        view.text =yearMonth
    }
}


