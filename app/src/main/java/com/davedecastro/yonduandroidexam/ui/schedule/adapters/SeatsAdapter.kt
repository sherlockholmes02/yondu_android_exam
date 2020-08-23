package com.davedecastro.yonduandroidexam.ui.schedule.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davedecastro.yonduandroidexam.R
import com.davedecastro.yonduandroidexam.databinding.ItemSeatsBinding

class SeatsAdapter : ListAdapter<String, SeatsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_seats,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val item = currentList[holder.adapterPosition]

            holder.binding.seat = item

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    class ViewHolder(val binding: ItemSeatsBinding) : RecyclerView.ViewHolder(binding.root)
}