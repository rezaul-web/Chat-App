package com.example.chatapp.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _firebaseDatabase = Firebase.database
    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channel = _channels.asStateFlow()

    init {
        getChannels()
    }

    private fun getChannels() {
        _firebaseDatabase.getReference("channels").get().addOnSuccessListener { it ->
            val list = mutableListOf<Channel>()
            it.children.forEach { data->
                val channel = Channel(
                    data.key!!,data.value.toString()
                )
                list.add(
                    channel
                )
            }
            _channels.value = list
        }

    }
}