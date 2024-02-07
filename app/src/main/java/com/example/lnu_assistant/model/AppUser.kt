package com.example.lnu_assistant.model

import android.app.Application

class AppUser: Application() {
    var userId: String? = null
    var userName: String? = null
    var userEmail: String? = null

    companion object{
        var instance: AppUser? = null

            get() {
                if (field == null) {
                    instance = AppUser()
                }

                return field
            }

            private set
    }
}