package com.example.chateo_app.chat

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chateo_app.chat.ChatCard.ChatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldChatTextScreen( modifier: Modifier = Modifier) {

    Scaffold(topBar = { TopAppBar(title = { })}) { innerpadding ->
        
    }
}

@Composable
fun ChatTextScreen(modifier: Modifier = Modifier) {
    
}

@Preview(showBackground = true)
@Composable
private fun Prev_ChatTextScreen () {
    ScaffoldChatTextScreen( )
}