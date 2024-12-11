package com.example.ideatapp.presentation.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ideatapp.data.model.DataItem
import com.example.ideatapp.databinding.ActivityRiwayatRowBinding
import com.example.ideatapp.presentation.ui.DetailHistoryActivity


class RiwayatAdapter : ListAdapter<DataItem, RiwayatAdapter.RiwayatViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val binding = ActivityRiwayatRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RiwayatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem)
    }

    inner class RiwayatViewHolder(private val binding: ActivityRiwayatRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: DataItem) {
            with(binding) {
                textFoodName.text = "Nama Makanan: ${dataItem.makanan}"
                textFoodCalories.text = "Kalori: ${dataItem.kalori.toString()}"

                Glide.with(root.context)
                    .load(dataItem.image)
                    .into(imageFood)
                historyDetailCard.setOnClickListener {
                    val intent = Intent(itemView.context, DetailHistoryActivity::class.java)
                    intent.putExtra("STORY_ID", dataItem.idHistory)
                    itemView.context.startActivity(intent)
                }
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
