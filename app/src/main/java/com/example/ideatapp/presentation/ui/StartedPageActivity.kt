package com.example.ideatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ideatapp.R

class StartedPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started_page)

        val button = findViewById<Button>(R.id.buttongetstarted)
        button.setOnClickListener {
            val intent = Intent(this, FirstSessionActivity::class.java)
            startActivity(intent)
        }
    }


}