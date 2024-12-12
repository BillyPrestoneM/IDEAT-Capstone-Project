package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ideatapp.BotNavActivity
import com.example.ideatapp.databinding.ActivityLoginBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.viewmodel.AuthViewModelImpl
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModelImpl by viewModel()

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()

    }

    private fun setupListeners() {
        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout
        val loginButton = binding.loginButton
        val registerTextIntent = binding.registerTextIntent

        loginButton.setOnClickListener {
            val email = emailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            when{
                email.isEmpty() -> {
                    emailInputLayout.error = "Email is required"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInputLayout.error = "Invalid email address"
                }
                password.isEmpty() -> {
                    passwordInputLayout.error = "Password is required"
                }
                else -> {
                    emailInputLayout.error = null
                    passwordInputLayout.error = null
                    lifecycleScope.launch {
                        authViewModel.login(email, password)
                    }
                }
            }
        }

        registerTextIntent.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        val loginButton = binding.loginButton
        val progressBar = binding.progressBar

        lifecycleScope.launch {
            authViewModel.user.collect { result ->
                when (result) {
                    is ResultUtil.Loading -> {
                        loginButton.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    is ResultUtil.Success -> {
                        loginButton.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        val token = result.data?.firstOrNull()?.registerData?.token
                        val name = result.data?.firstOrNull()?.registerData?.nama
                        token?.let {
                            authViewModel.saveToken(it, name ?: "")
                        }
                        val intent = Intent(this@LoginActivity, BotNavActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    is ResultUtil.Error -> {
                        loginButton.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {}
                }
            }
        }
    }
}
