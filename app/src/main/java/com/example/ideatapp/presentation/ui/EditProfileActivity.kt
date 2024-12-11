package com.example.ideatapp.presentation.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ideatapp.R
import com.example.ideatapp.databinding.ActivityEditProfileBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.viewmodel.EditProfileViewModelImpl
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModelImpl by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupTanggalLahirPicker()

        binding.saveButton.setOnClickListener {
            handleSaveButtonClick()
        }
    }

    private fun observeViewModel() {
        editProfileViewModel.editResult.observe(this) { result ->
            when (result) {
                is ResultUtil.Loading -> showLoading(true)
                is ResultUtil.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DataProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is ResultUtil.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupTanggalLahirPicker() {
        binding.tanggalLahirEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.tanggalLahirEditText.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun handleSaveButtonClick() {
        val nama = binding.namaEditText.text.toString().trim()
        val jenisKelamin = if (binding.lakiLakiRadioButton.isChecked) "Laki-laki" else "Perempuan"
        val tanggalLahir = binding.tanggalLahirEditText.text.toString().trim()
        val beratBadan = binding.beratBadanEditText.text.toString().toIntOrNull() ?: 0
        val tinggiBadan = binding.tinggiBadanEditText.text.toString().toIntOrNull() ?: 0

        when {
            nama.isEmpty() || tanggalLahir.isEmpty() || beratBadan <= 0 || tinggiBadan <= 0 -> {
                Toast.makeText(this, "Pastikan semua data diisi dengan benar", Toast.LENGTH_SHORT).show()
            }
            !isValidDateFormat(tanggalLahir) -> {
                Toast.makeText(this, "Tanggal lahir harus dalam format YYYY-MM-DD", Toast.LENGTH_SHORT).show()
            }
            else -> {
                editProfileViewModel.editProfile(nama, jenisKelamin, tanggalLahir, beratBadan, tinggiBadan)
            }
        }
    }

    private fun isValidDateFormat(date: String): Boolean {
        return date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.scrollView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
