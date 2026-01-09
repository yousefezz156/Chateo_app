package com.example.chateo_app.profileaccount.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.Uri
import coil3.compose.AsyncImage
import com.example.chateo_app.R


@Composable
fun ProfileImage(selectedImageUri: Uri?,onClick: () -> Unit,modifier: Modifier = Modifier) {
    Box {
        // Profile Circle
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(color = colorResource(id = R.color.offWhite)),
            contentAlignment = Alignment.Center
        ) {
            if(selectedImageUri!=null){
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop, // crop to fit circle
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            }else {
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = null,
                    modifier = modifier.size(50.dp)
                )
            }
        }

        // Plus Icon
        Box(
            modifier = Modifier
                .size(20.dp) // Size of the Plus Icon background
                .clip(CircleShape)
                .background(Color.Black) // Background for the plus icon
                .border(1.dp, Color.Black, CircleShape) // Optional border for styling
                .align(Alignment.BottomEnd) // Align to the bottom end of the parent Box
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.clickable { onClick()}
                //  modifier = Modifier.size(14.dp) // Adjust the size of the plus icon
            )
        }
    }
}