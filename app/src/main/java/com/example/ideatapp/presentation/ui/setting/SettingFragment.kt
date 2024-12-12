package com.example.ideatapp.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ideatapp.databinding.FragmentSettingBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.TokenRepository
import com.example.ideatapp.presentation.ui.DataProfileActivity
import com.example.ideatapp.presentation.ui.EditProfileActivity
import com.example.ideatapp.presentation.ui.LoginActivity
import com.example.ideatapp.presentation.ui.PasswordActivity
import com.example.ideatapp.presentation.viewmodel.ProfileViewModelImpl
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModelImpl by viewModel()
    private val tokenRepository: TokenRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.fetchProfile(
            nama = "Nama",
            email = "Email",
            jenisKelamin = "JenisKelamin",
            tanggalLahir = "TanggalLahir",
            beratBadan = 70,
            tinggiBadan = 170
        )

        observeProfile()

        binding.dataprofile.setOnClickListener {
            val intent = Intent(context, DataProfileActivity::class.java)
            startActivity(intent)
        }

        binding.editprofile.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.keamananakun.setOnClickListener {
            val intent = Intent(context, PasswordActivity::class.java)
            startActivity(intent)
        }

        binding.buttonlogout.setOnClickListener {
            lifecycleScope.launch {
                tokenRepository.clearSession()
            }
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    private fun observeProfile() {
        lifecycleScope.launch {
            profileViewModel.profileResult.observe(viewLifecycleOwner) { result ->
                Log.d("SettingFragment", "Observed Result: $result")
                when (result) {
                    is ResultUtil.Success -> {
                        val profile = result.data.firstOrNull()
                        Log.d("SettingFragment", "Profile Data: $profile")
                        binding.userName.text = profile?.data?.nama ?: "No Name"
                        binding.userEmail.text = profile?.data?.email ?: "No Email"
                    }
                    is ResultUtil.Error -> {
                        Log.e("SettingFragment", "Error: ${result.message}")
                    }
                    ResultUtil.Loading -> {
                        binding.userName.text = "Loading..."
                        binding.userEmail.text = "Loading..."
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
