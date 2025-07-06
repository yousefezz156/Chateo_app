package com.example.chateo_app.chat.mockData

import androidx.lifecycle.ViewModel
import com.example.chateo_app.DataNumbers.Numbers
import com.example.chateo_app.R

open class MockImages: ViewModel() {
   open fun mockImages(): List<Int> {
        var imageList = mutableListOf<Int>()

        imageList.add(R.drawable.images)
       imageList.add(R.drawable.musicfile)
       imageList.add(R.drawable.ic_launcher_background)

       return imageList
    }
}