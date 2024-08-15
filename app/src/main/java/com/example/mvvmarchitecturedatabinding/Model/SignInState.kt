package com.example.mvvmarchitecturedatabinding.Model

data class SignInState(
    val isSignInSuccessful: Boolean =false,
    val signInError:String? =null
)
