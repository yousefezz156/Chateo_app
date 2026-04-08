package com.example.chateo_app.verfication.presentation

import android.app.Activity
import android.util.Log
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chateo_app.verfication.domain.Numbers
import com.example.chateo_app.DataNumbers.NumbersList
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.R
import com.example.chateo_app.verfication.presentation.MVI.VerficationScreenIntent
import com.example.chateo_app.verfication.presentation.MVI.VerificationScreenViewModel

//import com.example.chateo_app.supabase.model.ApiResponse


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Verfication_phone(
    navController: NavController,
    verficationScreenViewModel: VerificationScreenViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current


    // val navOTP by authViewModel.navigateToOtp.collectAsState()
    var showNumList by remember {
        mutableStateOf(false)
    }
    var isSelected by remember {
        mutableStateOf<Numbers?>(null)
    }

    var phone = remember {
        mutableStateOf("")
    }


    val state by verficationScreenViewModel.state.collectAsState()

    var phoneCode by remember {
        mutableStateOf(state.defaultCode)
    }
    var total_phone by remember {
        mutableStateOf("")
    }

    //val isLoading by authViewModel.authresponse.collectAsState()

    var PhoneNumber = "";

    LaunchedEffect(state.numberSent) {
        Log.d("why crash network", "i am here in launched effect")
        if (state.numberSent) {
            navController.navigate("${AppRoutes.OTP}/${total_phone}")
        }
    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {


        Text(
            text = stringResource(id = R.string.Enter_Phone_Number),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.padding(12.dp))
        Text(
            text = stringResource(id = R.string.Please_confirm_your_country_code_and_enter),
            fontSize = 12.sp
        )
        Spacer(modifier = modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.your_phone_number), fontSize = 12.sp)
        Spacer(modifier = modifier.padding(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = modifier.padding(start = 10.dp))
            Box(
                modifier = Modifier
                    .width(74.dp)
                    .clip(shape = RoundedCornerShape(2.dp)) // Specify exact size to confine clickable area
                    .background(color = colorResource(id = R.color.offWhite))
                    .clickable {
                        VerficationScreenIntent.LoadNumCodeList
                        showNumList = true
                    }


            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = modifier.padding(top = 40.dp))
                    Text(
                        text = if (isSelected != null) isSelected?.num.toString() else state.defaultCode.toString(), fontSize = 16.sp, textAlign = TextAlign.Center
                    )



                    Log.d("why crash network", "${state.defaultCode}")

                }
            }

            Spacer(modifier = modifier.padding(8.dp))

            PhoneNumberTextField(phone)

        }
        Spacer(modifier = modifier.padding(8.dp))
        Button(
            onClick = {
                Log.d("phone number" , phone.value)
                total_phone = if(isSelected!=null)"${isSelected?.num.orEmpty()}${phone.value}".trim() else "${state.defaultCode.toString()}${phone.value}".trim()

                //val activity = context as Activity
                if (total_phone!=null&& phone.value !="") {
                    //authViewModel.sendPhoneNumber(total_phone, activity)

                    verficationScreenViewModel.onEvent(
                        VerficationScreenIntent.SendNumber(
                            total_phone
                        )
                    )
                    Log.d("why crash network", "i am here in button")

                } else {
                    Toast.makeText(
                        context,
                        "Please enter a valid phone number and country code",
                        Toast.LENGTH_SHORT
                    ).show()
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
            Text(stringResource(id = R.string.Send_OTP))
        }

        if (showNumList) {
            ModalBottomSheet(onDismissRequest = { showNumList = false }) {
                LazyCol(
                    number = state.listOfNum,
                    onClickNum = { selected ->
                        isSelected = selected
                        showNumList = false
                    },
                    isSelected = isSelected
                )
            }
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
    Row(
        modifier = modifier
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

@Composable
fun PhoneNumberTextField(phone: MutableState<String>, modifier: Modifier = Modifier) {

    var phone by phone
    BasicTextField(
        value = phone,
        onValueChange = { phone = it },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(id = R.color.offWhite))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                // Placeholder
                if (phone.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.phoneNumber),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Actual text field
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Verfication_phone_Prev() {
    Verfication_phone(
        navController = rememberNavController(),
        verficationScreenViewModel = viewModel()
    )
}