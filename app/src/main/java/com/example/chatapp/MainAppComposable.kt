package com.example.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.features.features.ChatScreen
import com.example.chatapp.features.signin.SignInScreen
import com.example.chatapp.features.signup.SignUpScreen
import com.example.chatapp.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if (currentUser != null) "home" else "login"
        NavHost(navController = navController, startDestination = start) {

            composable("login") {
                SignInScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("home") {
                HomeScreen(navController)
            }
            composable("chat/{channelId}") { backStackEntry ->
                val channelId = backStackEntry.arguments?.getString("channelId")
                if (channelId != null) {
                    ChatScreen(navController, channelId)
                }
            }
        }
    }
}
