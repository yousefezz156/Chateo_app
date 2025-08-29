package com.example.chateo_app.personnalchat.insiderChat.accesgallery

import android.content.ContentResolver.MimeTypeInfo
import android.content.Context
import android.media.browse.MediaBrowser.MediaItem
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontLoadingStrategy.Companion.Async
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.MimeTypeFilter
import androidx.core.view.size
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage

import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.R
import com.example.chateo_app.chat.presentation.mockData.MockImages

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun GalleryScreenScaffold(modifier: Modifier = Modifier) {

    Scaffold(topBar = {
        TopAppBar(title = { "later" }, actions = {})
    },
        bottomBar = {
            BottomAppBar {

            }
        }) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
        }

    }

}

@Composable
fun Gallery_screen(
    galleryViewModel: GalleryViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val ret by galleryViewModel.selectedItems.collectAsState()

    var description by remember {
        mutableStateOf("")
    }
//    var onClick by remember {
//        mutableStateOf(false)
//    }
    var show by remember {
        mutableStateOf(ret.keys.first())
    }
    val mockImages = MockImages()




    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    )
    {
        var mimeType = getMimeType(context, show)
        if (mimeType?.startsWith("image") == true) {
            AsyncImage(
                model = show,
                contentDescription = "",
                modifier = modifier
                    .size(width = 250.dp, height = 250.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        }else if(mimeType?.startsWith("video") == true){
            val exoPlayer = remember {
                ExoPlayer.Builder(context).build().apply {
                    val mediaItem = androidx.media3.common.MediaItem.fromUri(show)
                    setMediaItem(mediaItem)
                    prepare()
                }
            }
            DisposableEffect(Unit) {
                onDispose { exoPlayer.release() }
            }

            AndroidView(
                modifier = modifier
                    .fillMaxWidth()
                    .size(width = 250.dp, height = 250.dp),
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = true

                    }
                }
            )
        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            LazyRow(horizontalArrangement = Arrangement.Start, modifier = modifier.fillMaxWidth()) {
                items(ret.keys.toList()) { item ->
                    val mimeType = getMimeType(context, item)
                    if (mimeType?.startsWith("image") == true) {
                        AsyncImage(
                            model = item,
                            contentDescription = null,
                            modifier = modifier
                                .size(width = 75.dp, height = 75.dp)
                                .clickable {
                                    description="";
                                    show = item }
                                .padding(4.dp)
                                .clip(shape = RoundedCornerShape(8.dp))

                        )
                    } else {
                        Spacer(modifier.padding(8.dp))
                        val exoPlayer = remember {
                            ExoPlayer.Builder(context).build().apply {
                                val mediaItem = androidx.media3.common.MediaItem.fromUri(item)
                                setMediaItem(mediaItem)
                                prepare()
                            }
                        }
                        DisposableEffect(Unit) {
                            onDispose { exoPlayer.release() }
                        }

                        AndroidView(
                            modifier = modifier
                                .fillMaxWidth().clickable {
                                    description=""
                                    show=item
                                }
                                .size(width = 75.dp, height = 75.dp),
                            factory = { ctx ->
                                PlayerView(ctx).apply {
                                    player = exoPlayer
                                    useController = false

                                }
                            }
                        )
                    }

                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            // Text Field (80% width)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = description,
                    onValueChange = { description = it
                        galleryViewModel.updateSelectedItems(show,it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp) // text padding inside box
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // space between input and button

            // Confirm Button (fixed size)
            Button(
                onClick = {

                    galleryViewModel.onConfirm()
                    navController.navigate(AppRoutes.TEXTCHAT)
                },
                modifier = Modifier
                    .size(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue_def))
            ) {
                Text(text = "✓", color = Color.White) // Replace with icon if needed
            }
            Spacer(modifier = modifier.padding(bottom = 12.dp))
        }
    }
}


fun getMimeType(context: Context, uri: Uri): String? {
    return context.contentResolver.getType(uri)
}

@Preview
@Composable
private fun prv_screen() {
    Gallery_screen(galleryViewModel = viewModel(), navController = rememberNavController())
}