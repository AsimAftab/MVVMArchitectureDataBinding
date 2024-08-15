package com.example.mvvmarchitecturedatabinding.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mvvmarchitecturedatabinding.R

class SplashScreen : AppCompatActivity() {

    private val splashDelay: Long = 3000 // Duration of the splash screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        // Handle window insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Delay to show splash screen and then navigate to the next screen
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToOnboarding()
        }, splashDelay)
    }

    private fun navigateToOnboarding() {
        val intent = Intent(this, OnBoardingScreen::class.java)
        startActivity(intent)
        finish() // Close SplashScreen so it’s not in the back stack
    }
}
