package com.example.chatapp.model

data class Channel(
    val id: String="",
    val name: String,
    val dateCreated: Long= System.currentTimeMillis(),
)
