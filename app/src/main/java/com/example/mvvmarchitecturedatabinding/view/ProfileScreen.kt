package com.example.mvvmarchitecturedatabinding.view

import ProfileViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmarchitecturedatabinding.Model.UserData
import com.example.mvvmarchitecturedatabinding.R
import com.example.mvvmarchitecturedatabinding.services.AuthServiceImpl
import com.example.mvvmarchitecturedatabinding.services.ProfileViewModelFactory

class ProfileScreen : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var signOutButton: Button

    private val viewModel: ProfileViewModel by lazy {
        // Initialize AuthService implementation
        val authService = AuthServiceImpl()

        // Create ViewModelProvider.Factory
        val factory = ProfileViewModelFactory(authService)

        // Use factory to get ProfileViewModel
        ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_screen)

        // Bind views
        profileImageView = findViewById(R.id.profile_image)
        usernameTextView = findViewById(R.id.username_text)
        signOutButton = findViewById(R.id.sign_out_button)

        // Handle window insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observe user data
        viewModel.userData.observe(this) { userData ->
            updateUI(userData)
        }

        // Observe sign-out event
        viewModel.signOutEvent.observe(this) {
            navigateToSignInScreen()
        }

        // Handle sign out
        signOutButton.setOnClickListener {
            viewModel.signOut()
        }
    }

    private fun updateUI(userData: UserData?) {
        if (userData?.profilePictureUrl != null) {
            Glide.with(this)
                .load(userData.profilePictureUrl)
                .circleCrop()
                .into(profileImageView)
        }

        usernameTextView.text = userData?.username ?: "Unknown User"
    }

    private fun navigateToSignInScreen() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close ProfileScreen so it’s not in the back stack
    }
}
