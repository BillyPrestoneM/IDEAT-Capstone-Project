package com.example.ideatapp.startpage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ideatapp.R
import com.example.ideatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailInputLayout = binding.emailInputLayout
        val passwordInputLayout = binding.passwordInputLayout
        val loginButton = binding.loginButton
        val registerTextIntent = binding.registerTextIntent

        loginButton.setOnClickListener {
            val email = emailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    binding.emailInputLayout.error = getString(R.string.email_required)
                } else {
                    binding.emailInputLayout.error = null
                }
                if (password.isEmpty()) {
                    binding.passwordInputLayout.error = getString(R.string.password_required)
                } else {
                    binding.passwordInputLayout.error = null
                }
            }
        }
    }
}
