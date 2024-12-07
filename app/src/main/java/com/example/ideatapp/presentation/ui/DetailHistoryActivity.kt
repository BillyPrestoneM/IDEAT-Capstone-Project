package com.example.ideatapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ideatapp.databinding.ActivityDetailHistoryBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.viewmodel.DetailViewModelImpl
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHistoryActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModelImpl by viewModel()

    private lateinit var ivHistoryImage: ImageView
    private lateinit var tvHistoryName: TextView
    private lateinit var tvKaloriHistory: TextView
    private lateinit var tvtanggalHistory: TextView
    private lateinit var tvwaktuHistory: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivHistoryImage = binding.ivHistoryImage
        tvHistoryName = binding.tvHistoryName
        tvKaloriHistory = binding.tvKaloriHistory
        tvwaktuHistory = binding.tvwaktuHistory
        tvtanggalHistory = binding.tvtanggalHistory
        progressBar = binding.progressBar

        val storyId = intent.getStringExtra("STORY_ID") ?: ""
        Log.d("DetailStoryActivity", "Received story ID: $storyId")

        detailViewModel.fetchDetailHistory(storyId)

        detailViewModel.detailHistory.observe(this) { result ->
            when (result) {
                is ResultUtil.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }

                is ResultUtil.Success -> {
                    progressBar.visibility = View.GONE
                    result.data.data?.let { data ->
                        tvHistoryName.text = data.makanan ?: "Nama Tidak tersedia"
                        tvKaloriHistory.text = data.kalori ?: "Kalori Tidak tersedia"
                        tvwaktuHistory.text = data.jam ?: "Waktu Tidak tersedia"
                        tvtanggalHistory.text = data.tanggal ?: "Tanggal Tidak tersedia"
                        Glide.with(this)
                            .load(data.image)
                            .into(ivHistoryImage)
                    } ?: showToast("Data tidak tersedia")
                }
                is ResultUtil.Error -> {
                    progressBar.visibility = View.GONE
                    showToast("Error: ${result.message}")
                }
            }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}