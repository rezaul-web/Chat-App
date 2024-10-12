package com.example.chatapp.model

data class Channel(
    val name: String="",
    val id: String="",
    val dateCreated: Long= System.currentTimeMillis(),
)
