package com.hamu.walkforcats.ui.history

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hamu.walkforcats.R
import com.hamu.walkforcats.database.monthlyInfo
import com.hamu.walkforcats.databinding.FragmentItemBinding

import com.hamu.walkforcats.ui.history.placeholder.PlaceholderContent.PlaceholderItem


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    var data =  listOf<monthlyInfo>()
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
        holder.idView.text = "aaa"
        holder.contentView.text = "pppp"
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.year
        val contentView: TextView = binding.content

    }

}