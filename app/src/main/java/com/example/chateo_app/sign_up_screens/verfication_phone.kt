package com.example.chateo_app.sign_up_screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chateo_app.DataNumbers.Numbers
import com.example.chateo_app.DataNumbers.NumbersList
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.R
import com.example.chateo_app.supabase.ViewModel.SB_authViewModel
import com.example.chateo_app.supabase.model.ApiResponse


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Verfication_phone(modifier: Modifier = Modifier, authViewModel: SB_authViewModel = viewModel(), navController: NavController) {

    val navOTP by authViewModel.navigateToOtp.collectAsState()
    var showNumList by remember {
        mutableStateOf(false)
    }
    var isSelected by remember {
        mutableStateOf<Numbers?>(null)
    }

    var phone by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val isLoading by authViewModel.authresponse.collectAsState()

    var PhoneNumber ="";



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {


        Text(text = "Enter Your Phone Number", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = modifier.padding(12.dp))
        Text(text = "Please confirm your country code and enter", fontSize = 12.sp)
        Spacer(modifier = modifier.padding(8.dp))
        Text(text = "your phone number", fontSize = 12.sp)
        Spacer(modifier = modifier.padding(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = modifier.padding(start = 10.dp))
            Box(
                modifier = Modifier
                    .size(
                        width = 74.dp,
                        height = 46.dp
                    )
                    .clip(shape = RoundedCornerShape(2.dp)) // Specify exact size to confine clickable area
                    .background(color = colorResource(id = R.color.light_gray))
                    .clickable { showNumList = true }

            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = modifier.padding(top = 40.dp))
                    Text(text = isSelected?.num ?: "", textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = modifier.padding(8.dp))
            BasicTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
                    .height(46.dp)
                    .background(color = colorResource(id = R.color.light_gray)),
                textStyle = TextStyle(textAlign = TextAlign.Justify)
            )
        }
        Spacer(modifier = modifier.padding(8.dp))
        Button(
            onClick = {
                val total_phone = "${isSelected?.num.orEmpty()}$phone".trim()
                val activity = context as Activity
                if (isSelected != null ) {
                    authViewModel.sendPhoneNumber(total_phone, activity)
                    PhoneNumber = total_phone
                } else {
                    Toast.makeText(activity, "Please enter a valid phone number and country code", Toast.LENGTH_SHORT).show()
                }
                      },

            modifier = modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(start = 24.dp, end = 24.dp), colors = ButtonDefaults.buttonColors(
                colorResource(
                    id = R.color.blue_def
                )
            )
        ) {

            if (authViewModel.authresponse.collectAsState().value == ApiResponse.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
            } else {
                Text("Send OTP")
            }
        }

        if (showNumList) {
            ModalBottomSheet(onDismissRequest = { showNumList = false }) {
                LazyCol(
                    number = NumbersList().numList(),
                    onClickNum = { selected ->
                        isSelected = selected
                        showNumList = false
                    },
                    isSelected = isSelected
                )
            }
        }
    }
    // Navigate to OTP screen when OTP is sent
    LaunchedEffect(navOTP) {
        if(navOTP){
            authViewModel.navRest()
            navController.navigate("${AppRoutes.OTP}/$PhoneNumber")
        }
    }

}

@Composable
fun LazyCol(
    number: List<Numbers>,
    onClickNum: (Numbers) -> Unit,
    isSelected: Numbers?,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(number) { numbers ->
            NumCard(numbers = numbers, onClickNum = onClickNum, isSelected = isSelected == numbers)
            Spacer(modifier = modifier.padding(8.dp))
        }

    }
}

@Composable
fun NumCard(
    numbers: Numbers,
    onClickNum: (Numbers) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .size(20.dp)
        .clickable { onClickNum(numbers) }) {
        Text(text = numbers.flag, fontSize = 20.sp)
        Spacer(modifier = modifier.padding(8.dp))
        Text(text = numbers.num, fontSize = 16.sp)
        Spacer(modifier = modifier.weight(1f))
        if (isSelected) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Verfication_phone_Prev() {
    Verfication_phone(navController = rememberNavController())
}