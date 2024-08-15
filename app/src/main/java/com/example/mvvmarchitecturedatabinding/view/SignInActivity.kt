package com.example.mvvmarchitecturedatabinding.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.mvvmarchitecturedatabinding.R
import com.example.mvvmarchitecturedatabinding.databinding.ActivitySignInBinding
import com.example.mvvmarchitecturedatabinding.services.GoogleAuthUIClient
import com.example.mvvmarchitecturedatabinding.vm.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    // Initialize GoogleAuthUIClient
    private lateinit var googleAuthUIClient: GoogleAuthUIClient

    // Register the activity result launcher for Google Sign-In intent
    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        handleSignInResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable Edge-to-Edge content
        enableEdgeToEdge()

        // Initialize Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        // Handle window insets for full-screen experience
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize GoogleAuthUIClient
        val oneTapClient: SignInClient = Identity.getSignInClient(this)
        googleAuthUIClient = GoogleAuthUIClient(
            context = this,
            oneTapClient = oneTapClient
        )

        // Observe state changes from the ViewModel
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                if (state.isSignInSuccessful) {
                    // Handle successful sign-in, navigate to ProfileScreen
                    startActivity(Intent(this@SignInActivity, ProfileScreen::class.java))
                    finish() // Optional: Close the SignInActivity
                }

                if (state.signInError != null) {
                    // Show error message
                    binding.errorTextView.text = state.signInError
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }
        }

        // Handle sign-in button click
        binding.signInButton.setOnClickListener {
            initiateGoogleSignIn()
        }
    }

    private fun initiateGoogleSignIn() {
        lifecycleScope.launch {
            val intentSender = googleAuthUIClient.signIn()
            intentSender?.let {
                signInLauncher.launch(IntentSenderRequest.Builder(it).build())
            } ?: run {
                Toast.makeText(this@SignInActivity, "Google Sign-In unavailable.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignInResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                lifecycleScope.launch {
                    val signInResult = googleAuthUIClient.signInWithIntent(intent)
                    viewModel.onSignInResult(signInResult)
                }
            }
        } else {
            Toast.makeText(this, "Google Sign-In failed.", Toast.LENGTH_SHORT).show()
        }
    }
}
