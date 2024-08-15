package com.example.mvvmarchitecturedatabinding.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mvvmarchitecturedatabinding.R

class OnBoardingScreen : AppCompatActivity() {

    private lateinit var onboardingImage: ImageView
    private lateinit var onboardingText: TextView
    private lateinit var dotContainer: LinearLayout
    private lateinit var getStartedButton: Button

    private val images = listOf(
        R.drawable.img1, // Replace with your image resource IDs
        R.drawable.img2,
        R.drawable.img3
    )

    private val texts = listOf(
        "Welcome to Our App!",
        "Discover amazing features!",
        "Enjoy a seamless experience!"
    )

    private var currentImageIndex = 0
    private val splashDelay: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_boarding_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind views
        onboardingImage = findViewById(R.id.onboarding_image)
        onboardingText = findViewById(R.id.onboarding_text)
        dotContainer = findViewById(R.id.dot_container)
        getStartedButton = findViewById(R.id.get_started_button)

        // Initialize the first image and text
        updateUI()

        // Start image slideshow
        startImageSlideshow()

        // Set up button click listener
        getStartedButton.setOnClickListener {
            navigateToSignInScreen()
        }
    }

    private fun startImageSlideshow() {
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                currentImageIndex = (currentImageIndex + 1) % images.size
                updateUI()
                Handler(Looper.getMainLooper()).postDelayed(this, splashDelay)
            }
        }, splashDelay)
    }

    private fun updateUI() {
        onboardingImage.setImageResource(images[currentImageIndex])
        onboardingText.text = texts[currentImageIndex]

        dotContainer.removeAllViews()
        for (i in images.indices) {
            val dot = createDot(i == currentImageIndex)
            dotContainer.addView(dot)
        }
    }

    private fun createDot(isActive: Boolean): ImageView {
        val dot = ImageView(this)
        val size = resources.getDimensionPixelSize(R.dimen.dot_size)
        val margin = resources.getDimensionPixelSize(R.dimen.dot_margin)
        val params = LinearLayout.LayoutParams(size, size)
        params.setMargins(margin, margin, margin, margin)
        dot.layoutParams = params
        dot.setBackgroundColor(if (isActive) getColor(R.color.active_dot_color) else getColor(R.color.inactive_dot_color))
        return dot
    }

    private fun navigateToSignInScreen() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish() // Close OnboardingScreen so it’s not in the back stack
    }
}
