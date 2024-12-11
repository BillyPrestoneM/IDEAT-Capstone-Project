package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ideatapp.databinding.ActivityPasswordBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.viewmodel.PasswordViewModelImpl
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordBinding
    private val passwordViewModel: PasswordViewModelImpl by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordViewModel.passwordChangeResult.observe(this) { result ->
            when (result) {
                is ResultUtil.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultUtil.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                }
                is ResultUtil.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    if (result.message.contains("silahkan login kembali", true)) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        binding.saveButton.setOnClickListener {
            val oldPassword = binding.oldpassword.text.toString()
            val newPassword = binding.newpassword.text.toString()
            val confirmNewPassword = binding.confirmpassword.text.toString()

            if (oldPassword.isNotBlank() && newPassword.isNotBlank() && confirmNewPassword.isNotBlank()) {
                if (newPassword == confirmNewPassword) {
                    passwordViewModel.changePassword(oldPassword, newPassword, confirmNewPassword)
                } else {
                    Toast.makeText(this, "Password baru dan konfirmasi password tidak sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
