package com.hamu.walkforcats.ui.history

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.databinding.FragmentItemBinding



class MyItemRecyclerViewAdapter(list: List<monthlyInfo>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    var data = list
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.idView.text = item.yearMonth.toString()
        holder.contentView.text = item.percentOfMonthlyGoal.toString()
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.year
        val contentView: TextView = binding.content

    }

}