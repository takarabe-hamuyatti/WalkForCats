package com.example.walkforcats

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.walkforcats.utils.actionBarColorToStatusBarColor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBarColor = Color.parseColor("#9cc5c7")
        this.window.statusBarColor = actionBarColor.actionBarColorToStatusBarColor()
    }
}