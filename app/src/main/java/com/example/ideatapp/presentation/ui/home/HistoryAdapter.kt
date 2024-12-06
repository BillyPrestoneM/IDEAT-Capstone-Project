package com.example.ideatapp.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ideatapp.data.model.DataItem
import com.example.ideatapp.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<DataItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    fun addNewHistory(newHistory: DataItem) {
        val currentList = currentList.toMutableList()
        currentList.add(0, newHistory)
        submitList(currentList)
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: DataItem) {
            with(binding) {
                textFoodName.text = dataItem.makanan
                textFoodCalories.text = dataItem.kalori.toString()
                textFoodDate.text = dataItem.tanggal

                Glide.with(root.context)
                    .load(dataItem.image)
                    .into(imageFood)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.idHistory == newItem.idHistory
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
