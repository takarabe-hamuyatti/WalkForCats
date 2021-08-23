package com.example.walkforcats.ui.setting

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.walkforcats.R
import com.example.walkforcats.viewmodels.StepCountViewmodel

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel: StepCountViewmodel by navGraphViewModels(R.id.nest)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.getpreference()
            findNavController().navigate(R.id.action_settingsFragment_to_stepCountFragment)
        }
    }
}