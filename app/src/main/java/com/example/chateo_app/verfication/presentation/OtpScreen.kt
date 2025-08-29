package com.example.chateo_app.verfication.presentation

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.R

@Composable
fun OtpTextField(value: String, onValueChange: (String) -> Unit,focusRequester: FocusRequester, nextFocusRequester: FocusRequester?=null) {
    Box(
        modifier = Modifier
            .size(46.dp)  // Increased size for better visibility
            .clip(CircleShape)
            .border(1.dp, Color.Gray, CircleShape),  // Adding a visible border
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = { if (it.length <= 1) onValueChange(it)
                if(it.isNotBlank()){ nextFocusRequester?.requestFocus()} },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black , textAlign = TextAlign.Center // Ensure text is visible
            ),
            singleLine = true,
            modifier = Modifier.focusRequester(focusRequester)
        )
    }
}

@Composable
fun Otp_screen(navController: NavController, total_phone: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    //val verifyOtp by authViewModel.verifyOtpSuccessfully.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Enter Code", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = modifier.padding(8.dp))
        Text(text = "We have sent you an SMS with the code ")
        Spacer(modifier = modifier.padding(4.dp))
        Text(text = "to $total_phone")
        var text1 by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var text3 by remember { mutableStateOf("") }
        var text4 by remember { mutableStateOf("") }
        var text5 by remember { mutableStateOf("") }
        var text6 by remember { mutableStateOf("") }
        val focus1 = remember { FocusRequester() }
        val focus2 = remember { FocusRequester() }
        val focus3 = remember { FocusRequester() }
        val focus4 = remember { FocusRequester() }
        val focus5 = remember { FocusRequester() }
        val focus6 = remember { FocusRequester() }
        Spacer(modifier = modifier.padding(8.dp))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OtpTextField(value = text1, onValueChange = { text1=it}, focusRequester = focus1, nextFocusRequester = focus2)
            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text2, onValueChange = { text2=it}, focusRequester = focus2, nextFocusRequester = focus3)
            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text3, onValueChange = { text3=it}, focusRequester = focus3, nextFocusRequester = focus4)
            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text4, onValueChange = { text4=it}, focusRequester = focus4, nextFocusRequester = focus5)
            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text5, onValueChange = { text5=it}, focusRequester = focus5, nextFocusRequester = focus6)
            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text6, onValueChange = { text6=it}, focusRequester = focus6)
        }
        Spacer(modifier = modifier.padding(8.dp))
        Text(
            text = "Resend code",
            modifier.clickable { },
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = modifier.padding(20.dp))
        Button(
            onClick = {
                val otp = text1 + text2 + text3 + text4 + text5 + text6
                val activity = context as Activity
                if (text1.length == 1 && text2.length == 1 && text3.length == 1 && text4.length == 1 && text5.length == 1 && text6.length == 1  /*check the confirmation code*/) {

                     //authViewModel.verifyOtp(otp, total_phone ,activity)
                    navController.navigate(AppRoutes.PROFILE)

                } else {
                    Toast.makeText(
                        activity,
                        "Please enter a valid phone number and country code",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .size(52.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue_def))
        ) {

            Text("Verify OTP")

        }

    }
//    LaunchedEffect(verifyOtp) {
//        if (verifyOtp) {
//            authViewModel.verifyOtpBool()
//            navController.navigate(AppRoutes.PROFILE) // Navigate to the next screen
//
//        }
//    }

}

@Preview(showBackground = true)
@Composable
fun Ot_screen_prev() {


    Otp_screen(
        total_phone = "01036985214",
        navController = rememberNavController()
    )
}
