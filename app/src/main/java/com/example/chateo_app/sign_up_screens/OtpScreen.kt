package com.example.chateo_app.sign_up_screens

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.example.chateo_app.supabase.ViewModel.SB_authViewModel

@Composable
fun OtpTextField(value: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(46.dp)  // Increased size for better visibility
            .clip(CircleShape)
            .border(1.dp, Color.Gray, CircleShape),  // Adding a visible border
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = { if (it.length <= 1) onValueChange(it) },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Black , textAlign = TextAlign.Center // Ensure text is visible
            ),
            singleLine = true
        )
    }
}

@Composable
fun Otp_screen(navController: NavController, authViewModel: SB_authViewModel = viewModel(),total_phone: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val verifyOtp by authViewModel.verifyOtpSuccessfully.collectAsState()


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
        Spacer(modifier = modifier.padding(8.dp))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OtpTextField(value = text1) {
                text1=it
            }
            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text2) {
                text2=it
            }

            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text3) {
                text3=it
            }

            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text4) {
                text4=it
            }

            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text5) {
                text5=it
            }

            Spacer(modifier = modifier.padding(8.dp))
            OtpTextField(value = text6) {
                text6=it
            }

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

                     authViewModel.verifyOtp(otp, total_phone ,activity)

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
    LaunchedEffect(verifyOtp) {
        if (verifyOtp) {
            authViewModel.verifyOtpBool()
            navController.navigate(AppRoutes.PROFILE) // Navigate to the next screen

        }
    }

}

@Preview(showBackground = true)
@Composable
fun Ot_screen_prev() {


    Otp_screen(
        total_phone = "01036985214",
        navController = rememberNavController()
    )
}
