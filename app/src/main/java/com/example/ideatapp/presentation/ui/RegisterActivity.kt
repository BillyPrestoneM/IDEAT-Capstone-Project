package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ideatapp.databinding.ActivityRegisterBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.viewmodel.AuthViewModelImpl
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModelImpl: AuthViewModelImpl by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        val nameInputLayout = binding.nameInputLayout
        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout
        val confirmInputLayout = binding.confirmInputLayout
        val registerButton = binding.loginButton

        registerButton.setOnClickListener {
            val name = nameInputLayout.editText?.text.toString().trim()
            val email = emailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()
            val confirmPassword = confirmInputLayout.editText?.text.toString().trim()

            when {
                name.isEmpty() -> {
                    nameInputLayout.error = "Name is required"
                }
                email.isEmpty() -> {
                    emailInputLayout.error = "Email is required"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInputLayout.error = "Invalid email address"
                }
                password.isEmpty() -> {
                    passwordInputLayout.error = "Password is required"
                }
                password != confirmPassword -> {
                    confirmInputLayout.error = "Passwords do not match"
                }
                else -> {
                    nameInputLayout.error = null
                    emailInputLayout.error = null
                    passwordInputLayout.error = null
                    confirmInputLayout.error = null

                    lifecycleScope.launch {
                        authViewModelImpl.register(name, email, password, confirmPassword)
                    }
                }
            }
        }
    }



    private fun observeViewModel() {
        val registerButton = binding.loginButton
        val progressBar = binding.progressBar

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModelImpl.user.collect { result ->
                    when (result) {
                        is ResultUtil.Loading -> {
                            registerButton.isEnabled = false
                            progressBar.visibility = View.VISIBLE
                        }

                        is ResultUtil.Error -> {
                            registerButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@RegisterActivity, result.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is ResultUtil.Success -> {
                            registerButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@RegisterActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                            navigateToLogin()
                        }

                        else -> {
                            registerButton.isEnabled = true
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
