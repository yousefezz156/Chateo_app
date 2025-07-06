package com.example.chateo_app.chat.insiderChat.accesgallery

import android.media.MediaPlayer
import android.media.browse.MediaBrowser.MediaItem
import android.net.Uri
import android.provider.MediaStore.Video.Media
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GalleryViewModel: ViewModel() {

    val _selectedItems = MutableStateFlow<HashMap<Uri,String?>>(hashMapOf())
    val selectedItems: StateFlow<HashMap<Uri, String?>> = _selectedItems

    var userSend = mutableStateOf(false);
    fun setSelectedItems(media: HashMap<Uri,String?>){
   _selectedItems.value=media

    }

    fun onConfirm(){
        userSend.value = true
    }
}