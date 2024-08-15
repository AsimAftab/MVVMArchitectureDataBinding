package com.example.mvvmarchitecturedatabinding.services

import ProfileViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileViewModelFactory(
    private val authService: AuthService
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
