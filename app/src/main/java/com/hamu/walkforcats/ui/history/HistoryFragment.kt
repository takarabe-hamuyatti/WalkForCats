package com.hamu.walkforcats.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.hamu.walkforcats.R
import com.hamu.walkforcats.databinding.FragmentHistoryBinding
import com.hamu.walkforcats.databinding.FragmentStepCountBinding
import com.hamu.walkforcats.ui.history.placeholder.PlaceholderContent
import com.hamu.walkforcats.viewmodels.StepCountViewmodel

/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val viewModel: StepCountViewmodel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentHistoryBinding.bind(view).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
            val adapter = MyItemRecyclerViewAdapter()
            it.list.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(context)
                it.setHasFixedSize(true)
                it.itemAnimator = DefaultItemAnimator()
            }
        }
    }
}