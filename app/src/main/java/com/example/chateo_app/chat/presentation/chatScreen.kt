package com.example.chateo_app.chat.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chateo_app.Navigations.NavigationBottomBar
import com.example.chateo_app.R
import com.example.chateo_app.chat.presentation.ChatCard.ChatCard
import com.example.chateo_app.chat.presentation.ChatCard.ChatCardList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold_chat_screen(navController: NavController,modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Chats") }, actions = {
            Icon(
                painter = painterResource(id = R.drawable.addchat),
                contentDescription = null,
                modifier = modifier.size(24.dp)
            )
            Spacer(modifier = modifier.padding(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.relatedtochatscren),
                contentDescription = null,
                modifier = modifier.size(24.dp)
            )
        })
    },
//        bottomBar = {
//            BottomAppBar {
//                NavigationBottomBar(navController = navController, 1)
//            }
//        }
        ) { innerpadding ->
        Box(modifier = modifier.padding(innerpadding)) {
            LazyChatCol(navController= navController, ChatCardList().getChatCard())
        }
    }
}

@Composable
fun LazyChatCol(navController: NavController, chatCard : List<ChatCard>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(chatCard){ chats ->
            Chat_screen(chatCard = chats, navController)
        }
    }
}

@Composable
fun Chat_screen(chatCard: ChatCard, navController: NavController, viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), modifier: Modifier = Modifier) {

    val searchText by viewModel.searchText.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        Row() {
            Column {

                    Box(
                        modifier = modifier
                            .size(56.dp)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                            .border(
                                1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(12.dp),
                            )
                    ) {
                        Image(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            modifier = modifier
                                .align(
                                    Alignment.Center
                                )
                                .padding(16.dp)
                        )
                    }
                    Spacer(modifier = modifier.padding(8.dp))
                    Text("Your Story")

                    Spacer(modifier = modifier.padding(16.dp))
                    Divider()
                    Spacer(modifier = modifier.padding(16.dp))
                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)){
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    ) {
                        if (searchText.isEmpty()) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = "Search",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                        BasicTextField(
                            value = searchText,
                            onValueChange = { viewModel::search },
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = modifier.padding(16.dp))
                Card(modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {  }) {
                    Row(modifier = modifier.background(color = Color.White)) {
                        Box {
                            Box (modifier = modifier
                                .clip(RoundedCornerShape(8.dp))
                                .size(56.dp)){


                                Image(
                                    painter = painterResource(id = chatCard.image),
                                    contentDescription = null,
                                    modifier= modifier.size(56.dp)
                                )
                            }

//
                        }
                        Row(modifier = modifier.clickable {  }) {
                            Spacer(modifier = modifier.padding(12.dp))
                            Column {

                                Text(text = chatCard.name, fontWeight = FontWeight.Bold)
                                Spacer(modifier = modifier.padding(8.dp))
                                Text(
                                    text = chatCard.last_message,
                                    color = colorResource(id = R.color.light_gray)
                                )
                            }
                            Row(horizontalArrangement =Arrangement.End, modifier = modifier.fillMaxWidth() ) {
                                Column (modifier = modifier.padding(end = 8.dp)){

                                    Text(text = chatCard.date, color = Color.LightGray)
                                    Spacer(modifier = modifier.padding(8.dp))
                                    Box(modifier = modifier
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                        .size(18.dp)
                                        .align(
                                            Alignment.CenterHorizontally
                                        ), contentAlignment = Alignment.Center) {
                                        Text(text = "1")
                                    }
                                }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }



@Preview
@Composable
private fun Chat_screen_prev() {
    Scaffold_chat_screen(rememberNavController())
}