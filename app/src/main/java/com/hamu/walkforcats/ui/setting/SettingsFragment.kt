package com.hamu.walkforcats.ui.setting

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.hamu.walkforcats.R
import com.hamu.walkforcats.viewmodels.StepCountViewmodel
import dagger.hilt.android.AndroidEntryPoint

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel: StepCountViewmodel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dailyGoalPreference: EditTextPreference? = findPreference("dailyGoal")
        val monthlyGoalPreference: EditTextPreference? = findPreference("monthlyGoal")

        //実機で使ってみたところ、キーボードアプリを使っている場合数字以外も打ててしまいました。
        //そのため、プラスで数字以外を弾く処理を書いています。
        dailyGoalPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }

        monthlyGoalPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER

        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val text =  dailyGoalPreference?.text
            val text2 = monthlyGoalPreference?.text

            if(text?.toIntOrNull() == null  ||  text2?.toIntOrNull() == null) {
                AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage("数字を入力してください")
                    .show()
            }else {
                viewModel.getGoal()
                findNavController().navigateUp()
            }
        }
    }
}
