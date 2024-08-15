package com.example.mvvmarchitecturedatabinding.services

import com.example.mvvmarchitecturedatabinding.Model.UserData
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl : AuthService {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun getSignedInUser(): UserData {
        return try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                UserData(
                    userId = currentUser.uid,
                    username = currentUser.displayName ?: "Unknown User",
                    profilePictureUrl = currentUser.photoUrl?.toString()
                )
            } else {
                throw Exception("No user is signed in")
            }
        } catch (e: Exception) {
            // Log error
            e.printStackTrace()
            throw Exception("Failed to get signed-in user", e)
        }
    }

    override suspend fun signOut() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            // Log error
            e.printStackTrace()
            throw Exception("Failed to sign out", e)
        }
    }
}
