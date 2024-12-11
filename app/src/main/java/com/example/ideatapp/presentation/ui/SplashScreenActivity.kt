package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ideatapp.BotNavActivity
import com.example.ideatapp.databinding.ActivitySplashScreenBinding
import com.example.ideatapp.presentation.viewmodel.AuthViewModelImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModelImpl by viewModel()
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        authViewModel.fetchToken()

        lifecycleScope.launch {
            authViewModel.token.collectLatest { token ->
                if (token != null) {
                    delay(3000)
                    navigateToHome()
                } else {
                    delay(3000)
                    navigateToStartedPage()
                }
            }
        }
    }

    private fun navigateToStartedPage() {
        val intent = Intent(this, StartedPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, BotNavActivity::class.java)
        startActivity(intent)
        finish()
    }
}
