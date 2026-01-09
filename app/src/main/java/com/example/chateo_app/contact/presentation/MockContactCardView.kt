package com.example.chateo_app.contact.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.R
import com.example.chateo_app.contact.mockData.MockDataContact
import io.ktor.websocket.Frame

@Composable
fun MockContactCardView(navController: NavController,mockDataContact: MockDataContact,modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().padding(top=8.dp).clickable{navController.navigate(
        AppRoutes.TEXTCHAT)}) {
        Row(modifier= modifier.padding(8.dp)) {
            Box{
                Box {
                    AsyncImage(model = mockDataContact.image, contentDescription = null, contentScale = ContentScale.Crop, modifier = modifier.size(64.dp))
                }
                if(mockDataContact.showOnlineStatus) {
                    Box(modifier = modifier.align(Alignment.BottomEnd).padding(start = 8.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.status),
                            contentDescription = null,
                            modifier = modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = modifier.padding(start = 16.dp))
            Column {
                Text(text = mockDataContact.firstName + " " + mockDataContact.lastName, color = Color.Black)
                Spacer(modifier = modifier.padding(4.dp))
                Text(text = mockDataContact.lastSeen, color = Color.Gray)
            }
        }
    }
}

@Preview
@Composable
private fun CardPrev() {
    MockContactCardView(navController = rememberNavController(),mockDataContact = MockDataContact(R.drawable.ph1, "Yousef", "Ezz", true))
}