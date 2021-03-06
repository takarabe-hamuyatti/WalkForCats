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
import com.hamu.walkforcats.utils.confirmDialog
import com.hamu.walkforcats.viewmodels.HistoryViewModel
import com.hamu.walkforcats.viewmodels.ApplicationViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val stepCountviewModel: ApplicationViewmodel by activityViewModels()
    private val historyViewmodel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HistoryAdapter(historyViewmodel)
        FragmentHistoryBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.applicationviewModel = stepCountviewModel
            it.historyviewModel = historyViewmodel
            it.list.also { list ->
                list.adapter = adapter
                list.layoutManager = LinearLayoutManager(context)
                list.setHasFixedSize(true)
                list.itemAnimator = DefaultItemAnimator()
            }
        }
        stepCountviewModel.initWhenRedisplay()
        historyViewmodel.allMonthlyInfo.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        if (historyViewmodel.isFirstDisplay) {
            confirmDialog(requireContext(),
                getString(R.string.demo_data_supplement),
                getString(R.string.demo_data_howtochange)
            ) { historyViewmodel.changeiIsFirstDisplayToFalse() }
        }
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
            R.id.action_weather ->{
                findNavController().navigate(R.id.action_navigation_history_to_weatherInfoFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
