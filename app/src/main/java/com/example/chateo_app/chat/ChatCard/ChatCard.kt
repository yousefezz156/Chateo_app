package com.example.chateo_app.chat.ChatCard

import android.media.Image
import androidx.annotation.DrawableRes
import java.sql.Date

class ChatCard(
    var name: String,
    var last_message: String,
    var date: String,
    var status: Boolean,
   @DrawableRes var image: Int
)