package com.example.mvvmarchitecturedatabinding.vm

import androidx.lifecycle.ViewModel
import com.example.mvvmarchitecturedatabinding.Model.SignInResult
import com.example.mvvmarchitecturedatabinding.Model.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    // Private mutable state flow to manage the sign-in state
    private val _state = MutableStateFlow(SignInState())

    // Publicly exposed immutable state flow for the UI to observe
    val state = _state.asStateFlow()

    // Function to handle the result of a sign-in attempt
    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null, // Check if the sign-in was successful
                signInError = result.errorMessage // Capture any error message if present
            )
        }
    }

    // Function to reset the state to its initial values
    fun resetState() {
        _state.update { SignInState() }
    }
}
