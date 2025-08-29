package com.example.chateo_app.contact.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.chateo_app.R
import com.example.chateo_app.contact.mockData.MockDataContact
import io.ktor.websocket.Frame

@Composable
fun MockContactCardView(mockDataContact: MockDataContact,modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row {
            AsyncImage(model = mockDataContact.image, contentDescription = null, contentScale = ContentScale.Crop)
            Spacer(modifier = modifier.padding(start = 16.dp))
            Column {
                Text(text = mockDataContact.firstName + mockDataContact.lastName, color = Color.Black)
                Spacer(modifier = modifier.padding(4.dp))
                Text(text = mockDataContact.lastSeen, color = Color.Green)
            }
        }
    }
}

@Preview
@Composable
private fun CardPrev() {
    MockContactCardView(mockDataContact = MockDataContact(R.drawable.ph1, "Yousef", "Ezz", true))
}