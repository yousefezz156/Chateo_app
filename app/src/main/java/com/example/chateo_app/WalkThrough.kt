package com.example.chateo_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Walkthrough (modifier: Modifier = Modifier) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.walkthrough), contentDescription = null, modifier.size(250.dp))

        Spacer(modifier = modifier.padding(42.dp))
        Text(
            text = " Connect easily with your family and friends over countries",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            maxLines = 3,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.padding(46.dp))

        Text(text=" Terms & Privacy Policy")
        Spacer(modifier = modifier.padding(16.dp))
        Button(onClick = { /*TODO*/ }, modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .size(56.dp), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue_def)))
        {
            Text(text = "Start Messaging")
        }



    }

}

@Preview(showSystemUi = true)
@Composable
private fun WalkThroughPrev()
{
    Walkthrough()
}