package com.hamu.walkforcats.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hamu.walkforcats.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

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

    @BindingAdapter("changeColorWhenMax")
    @JvmStatic
    fun changeColorWhenMax(view: com.mikhaellopez.circularprogressbar.CircularProgressBar,
                           percentString:String){
        val percent = percentString.dropLast(3).toInt()
        if(percent > 100) {
            view.progressBarColor = android.graphics.Color.parseColor("#FFF450")
        }
    }

    @BindingAdapter("changeToString")
    @JvmStatic
    fun changeToString(view:TextView,goal: Float) {
        var tmp = goal.toInt()
        view.text = tmp.toString()
    }

    @BindingAdapter("formatYearandMonth")
    @JvmStatic
    fun formatYearandMonth(view:TextView,date: LocalDate){
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM")
        val yearMonth = date.format(formatter).toString()
        view.text =yearMonth
    }

    @BindingAdapter("changeViewVisible")
    @JvmStatic
    fun changeViewVisible(view:View,isVisible:Boolean) {
        if (isVisible == true) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    @BindingAdapter("setCatView","isChangecat")
    @JvmStatic
    fun setCatView(imageView: ImageView,percentString:String,isChangecat:Boolean){
        val percent = percentString.dropLast(3).toInt()
        var imgResource by Delegates.notNull<Int>()
        if(isChangecat) {
            if (10 > percent) { imgResource = R.drawable.realcat1 }
            else if (30 > percent) { imgResource = R.drawable.realcat2 }
            else if (60 > percent) {imgResource = R.drawable.realcat3 }
            else if (80 > percent) { imgResource = R.drawable.realcat4 }
            else { imgResource = R.drawable.realcat5 }
        }else{
            if (10 > percent) { imgResource = R.drawable.whitecat1 }
            else if (30 > percent) { imgResource = R.drawable.whitecat2 }
            else if (60 > percent) { imgResource = R.drawable.whitecat3 }
            else if (80 > percent) { imgResource = R.drawable.whitecat4 }
            else { imgResource = R.drawable.whitecat5 }
        }
        imageView.setImageResource(imgResource)
    }

    @BindingAdapter("setCatViewForRecycler","isChangecat")
    @JvmStatic
    fun setCatViewForRecycler(imageView: ImageView,percent:Float,isChangecat: Boolean){
        var imgResource by Delegates.notNull<Int>()
        if(isChangecat){
            if(10 >= percent){  imgResource = R.drawable.realcat1}
            else if(30 >= percent) { imgResource = R.drawable.realcat2}
            else if(60 >= percent) { imgResource = R.drawable.realcat3}
            else if(80 >= percent){  imgResource = R.drawable.realcat4}
            else{imgResource = R.drawable.realcat5}
        }else{
            if(10 >= percent){  imgResource = R.drawable.whitecat1}
            else if(30 >= percent) { imgResource = R.drawable.whitecat2}
            else if(60 >= percent) { imgResource = R.drawable.whitecat3}
            else if(80 >= percent){  imgResource = R.drawable.whitecat4}
            else{imgResource = R.drawable.whitecat5}
        }
        imageView.setImageResource(imgResource)
    }
}




