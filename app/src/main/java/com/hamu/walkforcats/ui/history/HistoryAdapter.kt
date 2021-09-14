package com.hamu.walkforcats.ui.history

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hamu.walkforcats.database.MonthlyInfo
import com.hamu.walkforcats.databinding.FragmentItemBinding
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import com.hamu.walkforcats.viewmodels.HistoryViewModel
import javax.inject.Inject

class HistoryAdapter(private val viewModel: HistoryViewModel) : ListAdapter<MonthlyInfo, HistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder (
        private val binding:FragmentItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: MonthlyInfo,viewModel: HistoryViewModel) {
            binding.run{
                monthlyInfo = item
                percentForCat = item.monthlyPercent.toFloat()
                isChangeCat = viewModel.checkChangeCat()
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class DiffCallback : DiffUtil.ItemCallback<MonthlyInfo>() {

    override fun areItemsTheSame(oldItem: MonthlyInfo, newItem: MonthlyInfo): Boolean {
        return oldItem.yearMonth == newItem.yearMonth
    }
    override fun areContentsTheSame(oldItem: MonthlyInfo, newItem: MonthlyInfo): Boolean {
        return oldItem == newItem
    }
}
