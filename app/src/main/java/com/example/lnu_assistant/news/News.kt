package com.example.lnu_assistant.news

import com.google.firebase.Timestamp

data class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val timeAdded: Timestamp,
)