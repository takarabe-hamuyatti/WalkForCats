package com.hamu.walkforcats.ui.history

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.databinding.FragmentItemBinding
import com.hamu.walkforcats.repository.preference.PreferenceRepository
import java.time.YearMonth
import javax.inject.Inject

class MyItemRecyclerViewAdapter : ListAdapter<monthlyInfo, MyItemRecyclerViewAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder (
        private val binding:FragmentItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: monthlyInfo) {
            binding.run{
                yearMonth.text = makeYearmonthText(item.yearMonth)
                monthlyCount.text = item.stepCount.toString()
                monthlyGoal.text = item.monthlyGoal.toString()
                percentForCat = item.monthlyPercent
                val percenttext = item.monthlyPercent?.toInt().toString()
                percent.text = "$percenttext%"

                val pref = PreferenceManager.getDefaultSharedPreferences(itemView.context)
                isChangeCat = pref.getBoolean("changeCat",false)
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun makeYearmonthText(yearMonth: Int): java.lang.StringBuilder {
            val sb = StringBuilder()
            sb.append(yearMonth.toString())
            return sb.insert(4,"-")
        }
    }
}


class DiffCallback : DiffUtil.ItemCallback<monthlyInfo>() {

    override fun areItemsTheSame(oldItem: monthlyInfo, newItem: monthlyInfo): Boolean {
        return oldItem.yearMonth == newItem.yearMonth
    }
    override fun areContentsTheSame(oldItem: monthlyInfo, newItem: monthlyInfo): Boolean {
        return oldItem == newItem
    }
}
