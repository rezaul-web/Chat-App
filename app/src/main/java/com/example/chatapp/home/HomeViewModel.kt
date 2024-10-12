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
            it.children.forEach { data ->
                val channel = Channel(
                    name= data.child("name").value.toString(),
                    id =data.child("key").value.toString(),
                    dateCreated = data.child("dateCreated").value as Long
                )
                list.add(
                    channel
                )
            }
            _channels.value = list
        }
    }
        fun addChannel(channelName: String) {
            val channel = Channel(name = channelName)
           val key= _firebaseDatabase.getReference("channels").push().setValue(channel).
                   addOnCompleteListener{
                       if (it.isSuccessful){
                           getChannels()
                       }
                   }
        }



}