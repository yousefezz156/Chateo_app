package com.example.chateo_app.profileaccount.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.toCoilUri
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.R

@Composable
fun Profile_account(navController: NavController, modifier: Modifier = Modifier) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    // State to hold selected image URI
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher to open gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: android.net.Uri? ->
            selectedImageUri =uri?.toCoilUri()
        }
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Spacer(modifier = modifier.padding(bottom = 36.dp))
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
                    modifier = modifier.clickable { galleryLauncher.launch("image/*")}
                    //  modifier = Modifier.size(14.dp) // Adjust the size of the plus icon
                )
            }
        }
        Spacer(modifier = modifier.padding(12.dp))

        ScreenTextField(
            firstName = firstName, onValueChange = { firstName = it }, placeHolder = stringResource(
                id = R.string.firstName
            )
        )

        Spacer(modifier = modifier.padding(12.dp))
        ScreenTextField(
            firstName = lastName, onValueChange = { lastName = it }, placeHolder = stringResource(
                id = R.string.lastName
            )
        )
        Spacer(modifier = modifier.padding(32.dp))
        Button(
            onClick = { navController.navigate(route = AppRoutes.MAiN_CONTACT) },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .size(52.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue_def))
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ScreenTextField(
    firstName: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    modifier: Modifier = Modifier
) {
    TextField(value = firstName,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.offWhite),
            unfocusedContainerColor = colorResource(id = R.color.offWhite),
            focusedIndicatorColor = colorResource(id = R.color.offWhite),
            unfocusedIndicatorColor = colorResource(id = R.color.offWhite),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.offWhite),
            unfocusedLabelColor = colorResource(id = R.color.offWhite)
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.offWhite))
            .padding(horizontal = 24.dp),
        placeholder = { Text(text = placeHolder, color = Color.Gray) }
    )
}


@Preview(showBackground = true)
@Composable
private fun Profile_account_prev() {
    Profile_account(rememberNavController())
}