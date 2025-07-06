package com.example.chateo_app.chat.insiderChat

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.RoundedCorner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.chateo_app.Navigations.AppRoutes

import com.example.chateo_app.R
import com.example.chateo_app.chat.ScaffoldChatTextScreen
import com.example.chateo_app.chat.insiderChat.IconList.Iconlist
import com.example.chateo_app.chat.insiderChat.accesgallery.GalleryViewModel
import com.example.chateo_app.chat.insiderChat.accesgallery.Gallery_screen
import com.example.chateo_app.chat.insiderChat.accesgallery.getMimeType
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import java.io.File
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsiderChatScaffold(
    modifier: Modifier = Modifier, galleryViewModel: GalleryViewModel,insiderChatViewModel: InsiderChatViewModel,
    navController: NavController
) {




    var mediaUri by remember {
        mutableStateOf(galleryViewModel.selectedItems.value)
    }
    var showIcons by remember {
        mutableStateOf(false)
    }

    var context = LocalContext.current
    var isRecording by remember {
        mutableStateOf(false)
    }
    var audioFile by remember {
        mutableStateOf<File?>(null)
    }
    var recorder: MediaRecorder? by remember {
        mutableStateOf(null)
    }
    var drag by remember {
        mutableStateOf(0f)
    }

    var message by remember {
        mutableStateOf("")
    }
    var messages by remember {
        mutableStateOf(listOf<com.example.chateo_app.chat.insiderChat.Message>())
    }
    var selectedIcons by remember {
        mutableStateOf<HashMap<com.example.chateo_app.chat.insiderChat.IconList.Icon, String?>>(
            hashMapOf()
        )
    }


    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "yousef") }, actions = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = modifier.clickable { })
        })
    }, bottomBar = {
        if (!showIcons) {
            BottomAppBar {
                Row(modifier = modifier.fillMaxWidth()) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = colorResource(id = R.color.light_gray),
                        modifier = modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                showIcons = true;
                            }
                    )

                    Spacer(modifier = modifier.padding(horizontal = 6.dp))
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .weight(1f)
                            .background(
                                color = colorResource(id = R.color.light_gray),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        BasicTextField(
                            value = message, onValueChange = { message = it }, modifier = modifier
                                .padding(horizontal = 24.dp)
                                .background(
                                    colorResource(id = R.color.light_gray)
                                )
                                .align(Alignment.CenterStart)
                        )

                    }
                    Spacer(modifier = modifier.padding(horizontal = 10.dp))
                    if (message.isNotBlank()) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .clickable {

                                    messages +=
                                        com.example.chateo_app.chat.insiderChat.Message(
                                            id = UUID
                                                .randomUUID()
                                                .toString(),
                                            SenderID = "me",
                                            audioUrl = null,
                                            voiceUrl = null,
                                            mediaUri = null,
                                            text = message,
                                            document = null,
                                            location = null,
                                            contact = null,
                                            timestamp = System.currentTimeMillis()
                                        )
                                    message = ""
                                }
                        )
                    } else { // I should add a if statement if the record buttom is clicked i Don't know how it works nut i will figure it out.

                        Box(
                            modifier
                                .align(Alignment.CenterVertically)
                                .pointerInput(Unit) {
                                    detectDragGestures(onDragStart = {
                                        var file = File(context.cacheDir, "recorder_audio.mp3")
                                        audioFile = file
                                        recorder = MediaRecorder().apply {
                                            setAudioSource(MediaRecorder.AudioSource.MIC)
                                            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                                            setOutputFile(file.absolutePath)
                                            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                                            prepare()
                                            start()
                                        }
                                        isRecording = true
                                        drag = 0f
                                    },
                                        onDrag = { change, dragAmount ->
                                            drag += dragAmount.x
                                        },
                                        onDragEnd = {
                                            if (drag < -100f) {
                                                recorder?.apply {
                                                    stop()
                                                    reset()
                                                }
                                                audioFile?.delete()
                                            } else {
                                                recorder?.apply {
                                                    stop()
                                                    release()
                                                }
                                                audioFile?.let { file ->
                                                    val messageId = UUID.randomUUID().toString()
                                                    messages += Message(
                                                        id =messageId,
                                                        SenderID = "me",
                                                        audioUrl = null,
                                                        voiceUrl = file.absolutePath,
                                                        mediaUri = null,
                                                        text = null,
                                                        document = null,
                                                        location = null,
                                                        contact = null,
                                                        timestamp = System.currentTimeMillis()
                                                    )
                                                    insiderChatViewModel.getAmp(messageId = messageId, file = file.absolutePath)
                                                }
                                            }

                                            isRecording = false
                                            audioFile = null
                                            recorder = null
                                        })

                                }) {
                            if (isRecording) {
                                var voiceSeconds by remember {
                                    mutableStateOf(0)
                                }
                                var minutes by remember{
                                    mutableStateOf(0)
                                }
                                LaunchedEffect(isRecording) {
                                    while(isRecording){
                                        if(voiceSeconds == 59){
                                            minutes++
                                            voiceSeconds=0
                                        }
                                        delay(1000)
                                        voiceSeconds++
                                    }
                                }
                                BottomAppBar {
                                    Text(text = "Recording: ${minutes}:${voiceSeconds}s — swipe left to cancel")
                                }
                            } else {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,

                                    )
                            }

                            if (galleryViewModel.userSend.value) {
                                messages += com.example.chateo_app.chat.insiderChat.Message(
                                    id = UUID
                                        .randomUUID()
                                        .toString(),
                                    SenderID = "me",
                                    audioUrl = null,
                                    voiceUrl = null,
                                    mediaUri = mediaUri,
                                    text = null,
                                    document = null,
                                    location = null,
                                    contact = null,
                                    timestamp = System.currentTimeMillis()
                                )
                                mediaUri = hashMapOf()
                                galleryViewModel.userSend.value = false

                            }
                        }

                    }
                }
                LaunchedEffect(messages) {
                    if (!messages.isEmpty() && messages.last().SenderID == "me") {
                        delay(1000)
                        messages = messages + com.example.chateo_app.chat.insiderChat.Message(
                            id = UUID.randomUUID().toString(),
                            SenderID = "other",
                            audioUrl = null,
                            voiceUrl = null,
                            mediaUri = null,
                            text = "hello bro",
                            document = null,
                            location = null,
                            contact = null,
                            timestamp = System.currentTimeMillis()
                        )
                    }
                }
            }
        } else {
            LazyR(
                icon = Iconlist().getIcon(),
                onClose = { showIcons = false },
                galleryViewModel = galleryViewModel,
                navController = navController
            )

        }
    }) { innerPadding ->

        Box(modifier = modifier.padding(innerPadding)) {
            InsiderChatScreen(messages = messages, insiderChatViewModel = insiderChatViewModel, galleryViewModel = galleryViewModel)


        }
    }
}

@Composable
fun InsiderChatScreen(
    messages: List<com.example.chateo_app.chat.insiderChat.Message>,
    galleryViewModel: GalleryViewModel,
    insiderChatViewModel: InsiderChatViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_gray))
    ) {
        items(messages) { message ->
            MessageInput(message = message, insiderChatViewModel = insiderChatViewModel,galleryViewModel = galleryViewModel)
            Spacer(modifier = modifier.padding(2.dp))

        }

    }
}

@Composable
fun MessageInput(
    message: com.example.chateo_app.chat.insiderChat.Message, galleryViewModel: GalleryViewModel,
    insiderChatViewModel: InsiderChatViewModel,
    modifier: Modifier = Modifier,

    ) {


    val ret by galleryViewModel.selectedItems.collectAsState()

    val allAmp by insiderChatViewModel.storeAmp.collectAsState()



    var isPressed: Boolean = false
    var context: Context = LocalContext.current

    val color =
        if (message.SenderID == "me") colorResource(id = R.color.blue_def) else colorResource(id = R.color.white)
    var align = if (message.SenderID == "me") Arrangement.End else Arrangement.Start

    if (message.text != null) {
        Row(
            modifier = modifier
                .fillMaxWidth(), horizontalArrangement = align
        ) {
            Box(
                modifier = modifier
                    .background(color = color, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(text = message.text ?: "", color = Color.Black)
            }
        }
    } else if (message.voiceUrl != null) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 64.dp, end = 8.dp),
            horizontalArrangement = align
        ) {
            Box(
                modifier = modifier
                    .background(color = color, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier.align(Alignment.CenterStart).clickable {
                        insiderChatViewModel.playAudio(
                            messageId = message.id,
                            context,
                            message.voiceUrl
                        )
                    })

                allAmp[message.id]?.let { AudioGraphic(amplitudes = it, progress = 0f, onSeek ={} , modifier = modifier.padding(start = 24.dp)) }
            }
        }
    } else if (message.mediaUri != null) {

        message.mediaUri.forEach { i ->
            if (getMimeType(context, Uri.parse(i.toString()))?.startsWith("image") == true) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = align
                ) {
                    Box(
                        modifier = modifier
                            .background(color = color, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = i,
                            contentDescription = null,
                            modifier = modifier
                                .size(width = 250.dp, height = 250.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else if (getMimeType(context, Uri.parse(i.toString()))?.startsWith("video") == true) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 64.dp, end = 8.dp),
                    horizontalArrangement = align
                ) {
                    Box(
                        modifier = modifier
                            .background(color = color, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        val exoPlayer = remember {
                            ExoPlayer.Builder(context).build().apply {
                                val mediaItem =
                                    androidx.media3.common.MediaItem.fromUri(Uri.parse(i.toString()))
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
                                .height(250.dp),
                            factory = { ctx ->
                                PlayerView(ctx).apply {
                                    player = exoPlayer
                                    useController = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun LazyR(
    icon: List<com.example.chateo_app.chat.insiderChat.IconList.Icon>,
    modifier: Modifier = Modifier, onClose: () -> Unit, galleryViewModel: GalleryViewModel,
    navController: NavController
) {
    BottomAppBar(modifier = modifier
        .fillMaxWidth()
        .clickable { onClose() }) {
        androidx.compose.foundation.lazy.LazyRow {
            items(icon) { icons ->
                PlusIconContent(
                    icon = icons,
                    onClose = onClose,
                    galleryViewModel = galleryViewModel,
                    navController = navController
                )
                Spacer(modifier = modifier.padding(24.dp))
            }
        }
    }
}

@Composable
fun PlusIconContent(
    icon: com.example.chateo_app.chat.insiderChat.IconList.Icon,
    galleryViewModel: GalleryViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    var context = LocalContext.current

    var nav = false

    var selectedItemsUris by remember {
        mutableStateOf<HashMap<Uri,String?>>(hashMapOf())
    }


    val gallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->

            for(x in uris) {
                selectedItemsUris[x] = ""
            }

            galleryViewModel.setSelectedItems(selectedItemsUris)
            navController.navigate(AppRoutes.GALLERY)
        })



    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(52.dp)
                .background(color = colorResource(id = icon.color))
                .clickable {
                    when (icon.name) {
                        "Gallery" -> gallery.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageAndVideo
                            )
                        )

                    }

                },
            contentAlignment = Alignment.Center

        )
        {
            Image(
                painter = painterResource(id = icon.icon),
                contentDescription = null,
                modifier.size(25.dp)
            )


        }
        Spacer(modifier.padding(1.dp))
        Text(text = icon.name, fontSize = 10.sp)

    }
}



@Preview(showBackground = true)
@Composable
private fun InsiderChatScreenPrev() {
    InsiderChatScaffold(
        modifier = Modifier,
        galleryViewModel = viewModel(),
        insiderChatViewModel = viewModel(),
        navController = rememberNavController()
    )
}