package com.example.walkforcats.utils

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class PreferDialog(): PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //todo 数字以外を入力するまでダイアログを閉じれない実装をする
    }

}