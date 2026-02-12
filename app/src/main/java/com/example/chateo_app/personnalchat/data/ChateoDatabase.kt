package com.example.chateo_app.personnalchat.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chateo_app.personnalchat.domain.entites.MessageDetails

@Database(entities=[MessageDetails::class], version = 1, exportSchema = false)
abstract class ChateoDatabase: RoomDatabase() {

    abstract fun messageDao(): MessagesDao
}