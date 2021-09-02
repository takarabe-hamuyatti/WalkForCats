package com.hamu.walkforcats.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentHistoryBinding
import com.hamu.walkforcats.viewmodels.HistoryViewModel
import com.hamu.walkforcats.viewmodels.StepCountViewmodel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val stepCountviewModel: StepCountViewmodel by activityViewModels()
    private val historyViewmodel :HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MyItemRecyclerViewAdapter()
        FragmentHistoryBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.stepCountviewModel = stepCountviewModel
            it.historyviewModel = historyViewmodel
            it.list.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(context)
                it.setHasFixedSize(true)
                it.itemAnimator = DefaultItemAnimator()
            }
        }
        historyViewmodel.allMonthlyInfo.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
                historyViewmodel._isDataExist.value = false
            }
        })
    }
}
