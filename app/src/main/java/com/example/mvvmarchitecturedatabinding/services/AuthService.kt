package com.example.mvvmarchitecturedatabinding.services

import com.example.mvvmarchitecturedatabinding.Model.UserData

interface AuthService {
    suspend fun getSignedInUser(): UserData?
    suspend fun signOut()
}
