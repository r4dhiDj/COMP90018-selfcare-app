package com.example.breathingexercise


import android.os.Bundle
import android.webkit.WebView
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.breathingexercise.ui.theme.BreathingExerciseTheme
import com.example.selfcare.viewmodels.BreatheViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val breatheVM by viewModels<BreatheViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreathingExerciseTheme {
                BreatheExerciseScreen(breatheVM)
            }
        }
    }
}

@Composable
fun BreatheExerciseScreen(breatheVM: BreatheViewModel) {

    BreathingExerciseTheme {
        Scaffold (
            topBar = { TopAppBar {
                Spacer(modifier = Modifier.width(10.dp))
                Text("Breathe Exercise") } },
            backgroundColor = Color(192, 239, 250),
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Home, "Return to Home Screen")
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                if (!breatheVM.isStarted) {
                    settingScreen(breatheVM)
                } else {
                    exercisingScreen(breatheVM)
                }
            })
    }
}

@Composable
fun settingScreen(breatheVM: BreatheViewModel) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NumberWheelPicker(breatheVM)
        MusicCheckBox(breatheVM)
        Button(
            onClick = {
                breatheVM.startBreathing()
            })
        { Text("Start", fontSize = 16.sp) }
    }
}

@Composable
fun exercisingScreen(breatheVM: BreatheViewModel) {
    var secondsLeft by remember {
        mutableStateOf(breatheVM.numberPicked.times(60).minus(1).toLong())
    }



    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = if (breatheVM.isMusic) "Music On" else "Music Off")
        Spacer(modifier = Modifier.height(10.dp))
        Row{
            // coroutine, I don't really understand this...
            LaunchedEffect(secondsLeft,{true}) {
                if (secondsLeft >= 0) {
                    delay(1000L)
                    secondsLeft -= 1L
                } else {
                    breatheVM.resetToDefault()
                }
            }
            Text("${secondsLeft.toString()}")
        }
        Spacer(modifier = Modifier.height(10.dp))
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    loadUrl("file:///android_res/drawable/breathing.gif")
                }
            },
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text (text =  if (secondsLeft.mod(10) >= 5) "Breathe in" else "Breathe out" )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                breatheVM.resetToDefault()
            })
        { Text("Stop", fontSize = 16.sp) }
    }
        
}

@Composable
fun NumberWheelPicker(breatheVM: BreatheViewModel) {
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
                    setOnValueChangedListener {
                            numberPicker, oldVal, newVal -> breatheVM.setPicked(newVal)
                    }
                }
            },
            modifier = Modifier.width(40.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("min", fontSize = 20.sp)
    }
}

@Composable
fun MusicCheckBox(breatheVM: BreatheViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(0.dp, 40.dp)
    ) {
        Text("Music", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Checkbox(
            checked = breatheVM.isMusic,
            modifier = Modifier
                .size(24.dp),
            onCheckedChange = { breatheVM.toggleMusic() },  // should update the variable passed to start activity
        )
    }
}