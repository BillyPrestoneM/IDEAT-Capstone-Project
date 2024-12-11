package com.example.ideatapp.presentation.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.ideatapp.databinding.ActivityRiwayatBinding
import com.example.ideatapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import com.example.ideatapp.data.storage.AuthPreferencesToken
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.ui.LoginActivity

class RiwayatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var riwayatAdapter: RiwayatAdapter
    private val viewModel: HomeViewModel by viewModel()
    private val authPreference: AuthPreferencesToken by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        checkAuthentication()
    }

    private fun setupRecyclerView() {
        riwayatAdapter = RiwayatAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRiwayat.layoutManager = layoutManager
        binding.recyclerViewRiwayat.adapter = riwayatAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.historyList.observe(this@RiwayatActivity) { result ->
                when (result) {
                    is ResultUtil.Success -> {
                        riwayatAdapter.submitList(result.data)
                        binding.progressBar.visibility = View.GONE
                    }
                    is ResultUtil.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@RiwayatActivity, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultUtil.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun checkAuthentication() {
        lifecycleScope.launch {
            val token = authPreference.getToken()
            if (token.isNullOrEmpty()) {
                val intent = Intent(this@RiwayatActivity, LoginActivity::class.java)
                startActivity(intent)
                this@RiwayatActivity.finish()
            } else {
                viewModel.fetchHistory()
            }
        }
    }
}
