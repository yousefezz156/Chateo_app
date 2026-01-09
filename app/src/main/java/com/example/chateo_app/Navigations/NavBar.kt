package com.example.chateo_app.Navigations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chateo_app.R

@Composable
fun NavigationBottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val navBottomItem = listOf(

        NavBBData("Contacts", R.drawable.contacts),
        NavBBData("Chats", R.drawable.chat),
        NavBBData("Settings", R.drawable.settings)

    )

    var selectedIndexItem by remember {
        mutableStateOf(0)
    }

    NavigationBar(modifier = modifier.background(color = Color.White)){
        navBottomItem.forEachIndexed{index, item ->
            NavigationBarItem(
                selected =selectedIndexItem == index,
                onClick = { selectedIndexItem = index
                          if(selectedIndexItem == 0){
                              //navigate to contacts
                              navController.navigate("contact")
                              }else if(selectedIndexItem == 1){
                              //navigate to chats
                              navController.navigate("mainchat")

                          }else if(selectedIndexItem == 2){
                              //navigate to settings
                          }
                },
                icon = { if(selectedIndexItem != index){
                    item.icon.let { Icon(painter = painterResource(id = it), contentDescription =null , modifier.size(60.dp))}
                }else{
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = item.iconName )
                        Spacer(modifier.padding(2.dp))
                        Text(text = ".")
                    }
                }
                }
            )
        }
    }
}