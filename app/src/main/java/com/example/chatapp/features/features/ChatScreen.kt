package com.example.chatapp.features.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.R
import com.example.chatapp.model.Message
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatScreen(navController: NavController, channelId: String) {
    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color(0xFFF5F5F5))) { // Light gray background for the screen
            val viewModel: ChatViewModel = hiltViewModel()
            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(channelId)
            }
            val messages = viewModel.message.collectAsState()
            ChatMessages(
                messages = messages.value,
                onSendMessage = { message -> viewModel.sendMessage(channelId, message) }
            )
        }
    }
}

@Composable
fun ChatMessages(
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
) {
    val hideKeyboardController = LocalSoftwareKeyboardController.current
    val msg = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Reduce space between items
        ) {
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(Color(0xFFE0E0E0)), // Light gray background for input area
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = msg.value,
                onValueChange = { msg.value = it },
                modifier = Modifier.weight(1f).padding(8.dp),
                placeholder = { Text(text = "Type a message") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    hideKeyboardController?.hide()
                })
            )

            IconButton(onClick = {
                // Add functionality to choose video/image here
                // You can use an image picker library to select files
            }) {
                Icon(
                    Icons.Default.Add, // Replace with your icon resource for adding files
                    contentDescription = "Add Media",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Blue // Change icon color to your preferred color
                )
            }

            IconButton(onClick = {
                onSendMessage(msg.value)
                msg.value = ""
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "send", tint = Color.Blue)
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val isCurrentUser = message.senderId == FirebaseAuth.getInstance().currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Color(0xFF1E88E5) // A nice blue color for current user's messages
    } else {
        Color(0xFF43A047) // A nice green color for other users' messages
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 8.dp) // Reduced vertical padding
    ) {
        val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd

        Box(
            modifier = Modifier
                .padding(4.dp) // Reduced padding within the bubble
                .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                .align(alignment)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.avatar), // Your avatar image resource
                    contentDescription = "User Icon",
                    modifier = Modifier
                        .size(32.dp) // Smaller icon size
                        .padding(4.dp) // Reduced padding around the icon
                )

                Text(
                    text = capitalizeFirstCharacter(message.message),
                    color = Color.White,
                    modifier = Modifier.padding(4.dp) // Reduced padding for the message text
                )
            }
        }
    }
}

fun capitalizeFirstCharacter(input: String): String {
    return if (input.isEmpty()) {
        input
    } else {
        input[0].uppercaseChar() + input.substring(1)
    }
}
