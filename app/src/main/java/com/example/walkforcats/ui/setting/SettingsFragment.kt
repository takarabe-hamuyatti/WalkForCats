package com.example.walkforcats.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.walkforcats.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}