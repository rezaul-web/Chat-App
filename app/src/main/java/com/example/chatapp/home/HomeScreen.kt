package com.example.chatapp.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channel.collectAsState()
    val addChannel = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { addChannel.value = true },

                    ) {

                    Text(text = "+")
                }
            }
        },
        containerColor = Color.Black
    ) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black)  // Optional: Add background color
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Messages",
                        color = Color.White,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    TextField(
                        value = "", onValueChange = {},
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clip(RoundedCornerShape(40.dp)),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }, colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        )


                    )
                }

                items(channels.value) { channel ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Red.copy(alpha = 0.5f))
                            .clickable {
                                navController.navigate("chat/${channel.id}")
                            } // Optional: Add background for each item
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        val name = channel.name
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                        ) {
                            Text(
                                text = name.first().toString().uppercase(),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 24.sp
                            )
                        }
                        Text(
                            text = name[0].uppercase()+name.substring(1),
                            color = Color.White,
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Black),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
    if (addChannel.value) {
        ModalBottomSheet(
            onDismissRequest = { addChannel.value = false },
            sheetState = sheetState
        ) {
            AddChannel(onAddChannel = { channelName ->
                viewModel.addChannel(channelName)
                addChannel.value = false
            })
        }
    }
}

@Composable
fun AddChannel(modifier: Modifier = Modifier, onAddChannel: (String) -> Unit) {
    val channelName = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(value = channelName.value, onValueChange = { channelName.value = it },
            label = { Text("Channel Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onAddChannel(channelName.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }


    }
}

