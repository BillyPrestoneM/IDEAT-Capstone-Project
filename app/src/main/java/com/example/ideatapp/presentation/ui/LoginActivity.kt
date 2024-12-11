package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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


        authViewModel.fetchToken()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.token.collect { token ->
                    if (token != null) {
                        navigateHomeMain()
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout
        val loginButton = binding.loginButton

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
    }

    private fun observeViewModel() {
        val loginButton = binding.loginButton
        val progressBar = binding.progressBar

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.user.collect { result ->
                    when (result) {
                        is ResultUtil.Loading -> {
                            loginButton.isEnabled = false
                            progressBar.visibility = View.VISIBLE
                        }

                        is ResultUtil.Error -> {
                            loginButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT).show()
                        }

                        is ResultUtil.Success -> {
                            loginButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            Log.d("Login", "Login success")

                            authViewModel.fetchToken()
                            authViewModel.token.collect { token ->
                                if (!token.isNullOrEmpty()) {
                                    Log.d("Login", "Token found: $token")
                                    navigateHomeMain()
                                }
                            }
                        }

                        else -> {
                            loginButton.isEnabled = true
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun navigateHomeMain() {
        Intent(this, BotNavActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
