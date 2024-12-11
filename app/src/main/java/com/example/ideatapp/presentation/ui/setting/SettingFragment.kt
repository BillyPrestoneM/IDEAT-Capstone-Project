package com.example.ideatapp.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ideatapp.databinding.FragmentSettingBinding
import com.example.ideatapp.presentation.ui.DataProfileActivity
import com.example.ideatapp.presentation.ui.EditProfileActivity
import com.example.ideatapp.presentation.ui.PasswordActivity

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}