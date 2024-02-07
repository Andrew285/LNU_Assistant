package com.example.lnu_assistant.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.lnu_assistant.repository.UserRepository

class AppViewModel(
    application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository()
    fun singInWithEmailAndPassword(activity: Activity, email: String, password: String): Boolean {
        return userRepository.loginWithEmailAndPassword(activity, email, password)
    }

    fun singOut() {
        userRepository.signOut()
    }
}