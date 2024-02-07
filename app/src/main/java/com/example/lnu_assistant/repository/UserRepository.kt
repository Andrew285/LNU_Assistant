package com.example.lnu_assistant.repository

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.lnu_assistant.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository(
    val firebaseAuth: FirebaseAuth = Firebase.auth
) {

    fun loginWithEmailAndPassword(activity: Activity, email: String, password: String): Boolean {
        var result: Boolean = true
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.let {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            val user: AppUser = AppUser.instance!!
                            user.userId = firebaseAuth.currentUser!!.uid
                            user.userName = firebaseAuth.currentUser!!.displayName
                            user.userEmail = firebaseAuth.currentUser!!.email
                        } else {
                            Toast.makeText(activity.applicationContext, "Authentication is failed", Toast.LENGTH_SHORT).show()
                            result = false
                        }
                    }
            }
        }
        else {
            Toast.makeText(activity.applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
            result = false
        }

        return result
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}