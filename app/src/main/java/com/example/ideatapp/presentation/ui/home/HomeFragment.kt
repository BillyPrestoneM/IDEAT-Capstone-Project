package com.example.ideatapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ideatapp.data.storage.AuthPreferencesToken
import com.example.ideatapp.databinding.FragmentHomeBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.ui.LoginActivity
import com.example.ideatapp.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel: HomeViewModel by viewModel()
    private val authPreference: AuthPreferencesToken by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter()
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHistory.adapter = historyAdapter

        observeViewModel()
        checkAuthentication()

        binding.fabHistory.setOnClickListener {
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.historyList.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResultUtil.Success -> {
                        historyAdapter.submitList(result.data)
                        binding.progressBar.visibility = View.GONE
                    }
                    is ResultUtil.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
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
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else {
                viewModel.fetchHistory()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
