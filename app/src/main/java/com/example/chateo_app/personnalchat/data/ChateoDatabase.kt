package com.example.chateo_app.personnalchat.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.chateo_app.personnalchat.domain.MessageConverter
import com.example.chateo_app.personnalchat.domain.entites.MessageDetails

@Database(entities=[MessageDetails::class], version = 1, exportSchema = false)
@TypeConverters(MessageConverter::class)
abstract class ChateoDatabase: RoomDatabase() {

    abstract fun messageDao(): MessagesDao
}