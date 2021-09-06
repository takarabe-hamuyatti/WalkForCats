package com.hamu.walkforcats.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentHistoryBinding
import com.hamu.walkforcats.viewmodels.HistoryViewModel
import com.hamu.walkforcats.viewmodels.StepCountViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val stepCountviewModel: StepCountViewmodel by activityViewModels()
    private val historyViewmodel :HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        stepCountviewModel.getGoal()
        historyViewmodel.checkIsUseDemoData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MyItemRecyclerViewAdapter()
        FragmentHistoryBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.stepCountviewModel = stepCountviewModel
            it.historyviewModel = historyViewmodel
            it.list.also {list ->
                list.adapter = adapter
                list.layoutManager = LinearLayoutManager(context)
                list.setHasFixedSize(true)
                list.itemAnimator = DefaultItemAnimator()
            }
        }
        historyViewmodel.allMonthlyInfo.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        historyViewmodel.isUseDemoData.observe(viewLifecycleOwner,{
            if(it) historyViewmodel.useDemoData() else historyViewmodel.noUseDemoData()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(R.id.action_navigation_history_to_navigation_settings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
