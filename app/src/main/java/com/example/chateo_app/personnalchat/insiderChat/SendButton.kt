package com.example.chateo_app.personnalchat.insiderChat

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chateo_app.personnalchat.insiderChat.IconList.Icon

@Composable
fun SendButton(
    message: String,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (message.isNotBlank()) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send Message",
            modifier = modifier.clickable { onSend(message) }
        )
    }
}