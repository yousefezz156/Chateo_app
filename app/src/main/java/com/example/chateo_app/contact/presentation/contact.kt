package com.example.chateo_app.contact.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chateo_app.Navigations.NavigationBottomBar
import com.example.chateo_app.R
import com.example.chateo_app.contact.presentation.mvi.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold_contact(viewModel: ContactViewModel,navController: NavController,modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Contacts") }, actions = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null, modifier = modifier.clickable { }
            )
        })
    }, bottomBar = {
        BottomAppBar {
            NavigationBottomBar(navController = navController)
        }
    }) { innerpadding ->
        Box(modifier = modifier.padding(innerpadding)) {
            Contact_screen(navController=navController,viewModel=viewModel)
        }
    }

}

@Composable
fun Contact_screen(modifier: Modifier = Modifier, navController: NavController,viewModel: ContactViewModel) {
   // val searchText by viewModel.searchText.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal =16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(36.dp)
//                    .background( // Ensure uniform light gray background
//                        color = Color.LightGray,
//                        shape = RoundedCornerShape(4.dp)
//                    )
//                    .border(
//                        0.1.dp,
//                        color = Color.LightGray,
//                        shape = RoundedCornerShape(12.dp),
//                    )
//                    .padding(horizontal = 8.dp)
//            ) {
//                // Placeholder when searchText is empty
//                if (searchText.isEmpty()) {
//                    Row(modifier = modifier
//                        .fillMaxWidth()
//                        .align(Alignment.Center)) {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                        )
//                        Text(
//                            text = "Search",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
//                            modifier = Modifier.padding(top=4.dp)
//                        )
//                    }
//                }
//
//                // BasicTextField for user input
//                BasicTextField(
//                    value = searchText,
//                    onValueChange = viewModel::onSearchChange,
//                    modifier = Modifier
//                        .fillMaxWidth() // Fill the entire box
//                        .align(Alignment.CenterStart)
//                )
//            }

                // BasicTextField for user input
                TextField(
                    value = searchText,
                    onValueChange = {searchText=it},
                    placeholder = {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Search")
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.offWhite),
                        unfocusedContainerColor = colorResource(id = R.color.offWhite),
                        focusedIndicatorColor = colorResource(id = R.color.offWhite),
                        unfocusedIndicatorColor = colorResource(id = R.color.offWhite),
                        cursorColor = colorResource(id = R.color.black),
                        focusedLabelColor = colorResource(id = R.color.offWhite),
                        unfocusedLabelColor = colorResource(id = R.color.offWhite)
                    ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                )
            Spacer(modifier = modifier.padding(8.dp))

            }
        contactLazyColumn(navController = navController,viewModel = viewModel)
        }
    }

@Composable
fun contactLazyColumn(navController: NavController,viewModel: ContactViewModel,modifier: Modifier = Modifier) {

    val contact by viewModel.state.collectAsState()

    Log.d("contact", contact.mockContacts.size.toString())

    LazyColumn {
        items(contact.mockContacts){
            contact ->
            MockContactCardView(navController = navController,mockDataContact = contact)
            Log.d("contact", contact.toString())
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun Contact_screen_prev() {
    //Scaffold_contact(remember { ContactViewModel() })
}