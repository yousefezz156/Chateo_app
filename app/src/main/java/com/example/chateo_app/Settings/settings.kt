package com.example.chateo_app.Settings

import android.provider.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldSettings(modifier: Modifier = Modifier) {
    Scaffold(topBar = { TopAppBar(title = { "More" })}) { innerpadding ->

    }
}

@Composable
fun Settings(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
private fun Prev_settings () {

}