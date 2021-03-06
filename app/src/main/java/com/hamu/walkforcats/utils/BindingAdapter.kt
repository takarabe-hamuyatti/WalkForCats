package com.hamu.walkforcats.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hamu.walkforcats.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

object BindingAdapter {
    //円形のprogresbar 用です
    @BindingAdapter("setProgressWithAnimation")
    @JvmStatic
    fun setProgressWithAnimation(view: com.mikhaellopez.circularprogressbar.CircularProgressBar?,count:String) {
        view ?: return
        view.setProgressWithAnimation(count.toInt().toFloat())
    }
    @BindingAdapter("cpb_progress_max")
    @JvmStatic
    fun setProgressMax(view: com.mikhaellopez.circularprogressbar.CircularProgressBar?,goal:Float){
        view ?: return
        view.progressMax = goal
    }

    @BindingAdapter("changeColorWhenMax")
    @JvmStatic
    fun changeColorWhenMax(view: com.mikhaellopez.circularprogressbar.CircularProgressBar?,
                           percentString:String){
        view ?: return
        val percent = percentString.dropLast(3).toInt()
        if(percent > 100) {
            view.progressBarColor = android.graphics.Color.parseColor("#FFF450")
        }
    }

    @BindingAdapter("changeToString")
    @JvmStatic
    fun changeToString(view:TextView?,goal: Float) {
        view ?: return
        val tmp = goal.toInt()
        view.text = tmp.toString()
    }

    @BindingAdapter("formatYearandMonth")
    @JvmStatic
    fun formatYearandMonth(view:TextView?,date: LocalDate){
        view ?: return
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val yearMonth = date.format(formatter).toString()
        view.text =yearMonth
    }

    @BindingAdapter("makeYearmonthText")
    @JvmStatic
    fun makeYearmonthText(view:TextView?,yearMonth: Int) {
        view ?: return
        val sb = StringBuilder()
        sb.append(yearMonth)
        view.text =  sb.insert(4,"-")
    }

    @BindingAdapter("addPercentsymbolToText")
    @JvmStatic
    fun addPercentsymbolToText(view:TextView?,text:String) {
        view ?: return
        view.text =  "$text%"
    }

    @BindingAdapter("setCatView")
    @JvmStatic
    fun setCatView(view: ImageView?,catResource:Int){
        view ?: return
        view.setImageResource(catResource)
    }
}




