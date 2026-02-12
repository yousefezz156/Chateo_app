package com.example.chateo_app.personnalchat.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.chateo_app.personnalchat.domain.entites.MessageDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Query("SELECT * FROM message_table")
    fun getAllMessages():Flow<List<MessageDetails>>

    @Upsert
    suspend fun upsertMessage(message: MessageDetails)

}