package com.example.chateo_app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.chat.insiderChat.InsiderChatScaffold
import com.example.chateo_app.chat.insiderChat.accesgallery.Gallery_screen
import com.example.chateo_app.ui.theme.Chateo_appTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            0)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Chateo_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppRoutes()
//                    InsiderChatScaffold()

                }
            }
        }
    }
}

