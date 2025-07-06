package com.example.chateo_app.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold_contact(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Contacts") }, actions = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null, modifier = modifier.clickable { }
            )
        })
    }) { innerpadding ->
        Box(modifier = modifier.padding(innerpadding)) {
            Contact_screen()
        }
    }

}

@Composable
fun Contact_screen(modifier: Modifier = Modifier, viewModel: ContactViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val searchText by viewModel.searchText.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background( // Ensure uniform light gray background
                        color = Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        0.1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(horizontal = 8.dp)
            ) {
                // Placeholder when searchText is empty
                if (searchText.isEmpty()) {
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.padding(top=4.dp)
                        )
                    }
                }

                // BasicTextField for user input
                BasicTextField(
                    value = searchText,
                    onValueChange = viewModel::onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth() // Fill the entire box
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Contact_screen_prev() {
    Scaffold_contact()
}