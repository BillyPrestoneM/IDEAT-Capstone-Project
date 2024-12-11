package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ideatapp.databinding.ActivityDataProfileBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.viewmodel.ProfileViewModelImpl
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DataProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataProfileBinding
    private val profileViewModel: ProfileViewModelImpl by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()

        profileViewModel.fetchProfile(
            nama = "Default Nama",
            email = "default@example.com",
            jenisKelamin = "Pria",
            tanggalLahir = "2000-01-01",
            beratBadan = 70,
            tinggiBadan = 175
        )
    }

    private fun setupObservers() {
        profileViewModel.profileResult.observe(this) { result ->
            when (result) {
                is ResultUtil.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultUtil.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val profileResponse = result.data.firstOrNull()
                    profileResponse?.data?.let {
                        binding.namaEditText.setText(it.nama ?: "Belum Ada Data")
                        binding.emailEditText.setText(it.email ?: "Belum Ada Data")
                        binding.jenisKelaminEditText.setText(it.jenisKelamin ?: "Belum Ada Data")

                        // Format tanggalLahir
                        binding.tanggalLahirEditText.setText(formatDate(it.tanggalLahir ?: ""))

                        binding.beratBadanEditText.setText(it.beratBadan?.toString() ?: "Belum Ada Data")
                        binding.tinggiBadanEditText.setText(it.tinggiBadan?.toString() ?: "Belum Ada Data")
                    } ?: run {
                        Toast.makeText(this@DataProfileActivity, "Profile data is empty", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultUtil.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@DataProfileActivity, result.message, Toast.LENGTH_SHORT).show() // Menampilkan pesan error
                }
            }
        }
    }

    private fun setupListeners() {
        binding.editProfileButton.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun formatDate(dateString: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return try {
            val date = originalFormat.parse(dateString)
            targetFormat.format(date ?: Date())
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid Date"
        }
    }
}
