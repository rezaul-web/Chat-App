package com.example.chatapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channel.collectAsState()

    Scaffold(
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = {  },

                    ) {

                    Text(text = "+")
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)  // Optional: Add background color
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(channels.value) { channel ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Red.copy(alpha = 0.3f))
                            .clickable {
                                // navController.navigate("chat/${channel.id}")
                            } // Optional: Add background for each item
                            .padding(16.dp)

                    ) {
                        Text(text = channel.id, color = Color.Black)
                    }
                }
            }
        }
    }
}
