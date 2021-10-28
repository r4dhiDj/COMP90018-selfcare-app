package com.example.selfcare.presentation.components

import android.media.MediaPlayer
import android.os.Build
import android.os.Vibrator
import android.webkit.WebView
import android.widget.NumberPicker
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.selfcare.ui.theme.*
import com.example.selfcare.viewmodels.BreatheViewModel
import com.example.selfcare.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BreatheScreen(navController: NavController,
                  breatheVM: BreatheViewModel,
                  vibrator: Vibrator,
                  mediaPlayer: MediaPlayer,
                  mainViewModel: MainViewModel
) {
    var darkMode = mainViewModel.darkModeState.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Breathing",
                        style = MaterialTheme.typography.h1,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        breatheVM.resetToDefault()
                        navController.navigate(Screen.MenuScreen.route)
                        mediaPlayer.release()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                    }
                },
                backgroundColor = Green700
            )
        },

//            floatingActionButton = {
//                FloatingActionButton(onClick = {
//                    breatheVM.resetToDefault()
//                    navController.navigate(Screen.MenuScreen.route)
//                }) {
//                    Icon(Icons.Filled.Home, "Return to Menu")
//                }
//            },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            if (!breatheVM.isStarted) {
                settingScreen(breatheVM, darkMode)
            } else {
                exercisingScreen(breatheVM, vibrator, mediaPlayer, darkMode)
            }
        },
        backgroundColor = if (darkMode) Color(18,18,18) else Green200

    )
    BackHandler(enabled = true) {
        breatheVM.resetToDefault()
        mediaPlayer.pause()
        mediaPlayer.seekTo(0)
        navController.popBackStack()
        navController.navigate(Screen.MenuScreen.route)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun settingScreen(breatheVM: BreatheViewModel, darkMode: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Relax with this guided breathing exercise",
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(30.dp))
        NumberWheelPicker(breatheVM, darkMode)
        MusicCheckBox(breatheVM)
        Button(
            onClick = {
                breatheVM.startBreathing()
            })
        { Text("Start", style = MaterialTheme.typography.button) }
    }
}

@Composable
fun exercisingScreen(breatheVM: BreatheViewModel, vibrator: Vibrator, mediaPlayer: MediaPlayer, darkMode: Boolean) {

    if (breatheVM.isMusic) {
        mediaPlayer.start()
    }
    var ring_url = "file:///android_res/drawable/breathe_ring.gif"

    if (darkMode) {
        ring_url = "file:///android_res/drawable/breathe_ring_dark.gif"
    }


    var secondsLeft by remember {
        mutableStateOf(breatheVM.numberPicked.times(60).minus(1).toLong())
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Breathe as instructed by the animation",
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            // coroutine, I don't really understand this...
            LaunchedEffect(secondsLeft, { true }) {
                if (secondsLeft >= 0) {
                    if (secondsLeft.mod(10) == 4 || secondsLeft.mod(10) == 9) {
                        vibrator.vibrate(50)
                    }
                    delay(1000L)
                    secondsLeft -= 1L
                } else {
                    vibrator.vibrate(500)
                    breatheVM.resetToDefault()
                    mediaPlayer.pause()
                    mediaPlayer.seekTo(0)
                }
            }
            Text(
                text = "${secondsLeft.floorDiv(60)}" +
                        ":" +
                        "%02d".format(secondsLeft.mod(60)),
                style = MaterialTheme.typography.h3
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    loadUrl(ring_url)
                    setBackgroundColor(0x00000000)
                }
            },
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = if (secondsLeft.mod(10) >= 5) "Breathe out..." else "Breathe in...",
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                breatheVM.resetToDefault()
                mediaPlayer.pause()
                mediaPlayer.seekTo(0)},
        )
        { Text(text = "Stop", style = MaterialTheme.typography.button) }
    }

}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NumberWheelPicker(breatheVM: BreatheViewModel, darkMode: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(0.dp, 40.dp),
    ) {
        AndroidView(
            factory = { context ->
                NumberPicker(context).apply {
                    minValue = 1
                    maxValue = 5
                    // textSize = 1.5F // Requires API level 29, we are on 28 ><
                    scaleX = 1.3F
                    scaleY = 1.3F
                    setOnValueChangedListener { numberPicker, oldVal, newVal ->
                        breatheVM.setPicked(newVal)
                    }
                    textColor = if (darkMode) Color.White.toArgb() else Color.Black.toArgb()
                    selectionDividerHeight = 4
                }
            },
            modifier = Modifier.width(40.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("min", style = MaterialTheme.typography.h3)
    }
}

@Composable
fun MusicCheckBox(breatheVM: BreatheViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(0.dp, 40.dp)
    ) {
        Text(text = "Music", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.width(10.dp))
        Checkbox(
            checked = breatheVM.isMusic,
            modifier = Modifier
                .size(24.dp),
            onCheckedChange = { breatheVM.toggleMusic() },
        )
    }
}
