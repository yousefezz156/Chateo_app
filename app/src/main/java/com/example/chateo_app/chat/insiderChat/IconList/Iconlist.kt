package com.example.chateo_app.chat.insiderChat.IconList

import androidx.compose.ui.graphics.Color
import com.example.chateo_app.R

class Iconlist {
    fun getIcon() : List<Icon>{
        val list  = mutableListOf<Icon>()

        list.add(Icon("Gallery" , R.drawable.images , R.color.blue_def, null))
        list.add(Icon("Folder" , R.drawable.folder , R.color.teal_200 , null))
        list.add(Icon("Audio" , R.drawable.musicfile, R.color.purple_200, null ))
        list.add(Icon("Contact" , R.drawable.contacts, R.color.yellow, null ))
        list.add(Icon("Location" , R.drawable.pin , R.color.green, null))

        return list
    }
}