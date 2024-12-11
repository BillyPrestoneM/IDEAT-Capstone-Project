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
import com.example.ideatapp.presentation.ui.DataProfileActivity
import com.example.ideatapp.presentation.ui.EditProfileActivity
import com.example.ideatapp.presentation.ui.LoginActivity
import com.example.ideatapp.presentation.ui.PasswordActivity
import com.example.ideatapp.presentation.viewmodel.AuthViewModelImpl
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModelImpl by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            authViewModel.userName.collect { name ->
                binding.userName.text = name ?: "No Name"
                Log.d("SettingFragment", "Username: $name")
            }
        }

        lifecycleScope.launch {
            authViewModel.userEmail.collect { email ->
                binding.userEmail.text = email ?: "No Email"
                Log.d("SettingFragment", "Email: $email")
            }
        }


        fetchUserData()

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
            authViewModel.clearSession()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    private fun fetchUserData() {
        lifecycleScope.launch {
            authViewModel.fetchUserName()
            authViewModel.fetchUserEmail()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
