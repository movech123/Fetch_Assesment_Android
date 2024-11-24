package com.fetch_assesment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Class to display the no connection screen when there's no internet connection or an API request failure
class NoConnectionActivity : AppCompatActivity() {
    private lateinit var noConnectionMessage: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)

        noConnectionMessage = findViewById(R.id.noConnectionMessage)
        val refreshButton: Button = findViewById(R.id.refreshButton)
        val errorMessage = intent.getStringExtra("error_message")
        val wifiLogo: ImageView = findViewById(R.id.wifiLogo)
        noConnectionMessage.text = errorMessage

        // If there's no error message, hide the WiFi logo
        if (errorMessage != null) {
            wifiLogo.visibility = View.GONE
        }
        refreshButton.setOnClickListener {
            // Retry loading data by starting the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close NoConnectionActivity
        }
    }
}
