package com.example.selfcare.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.selfcare.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

//import org.json.simple.JSONObject;

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
// for text2speech
import android.app.Activity
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.selfcare.ui.theme.*


@Composable
fun ChatScreen(navController: NavController) {
    var user1Message by remember { mutableStateOf("") }
    var user2Message by remember { mutableStateOf("") }
    val chat = remember {mutableStateListOf(Message("Hi how can i help you?",true))}  // <-- mutableStateOf doesn't work

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Pink700)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "back",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    },
                    onClick = { navController.navigate(Screen.MenuScreen.route) }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "chat",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Chat",
                    fontFamily = IBMPlexMono,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        },
        content = {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    botArea(
                        userName = stringResource(id = R.string.user1_name),
                        avatar = R.drawable.robot,
                        message = user1Message,
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(8.dp)
                    )
                    UserArea(
                        userName = stringResource(id = R.string.user2_name),
                        avatar = R.drawable.girl,
                        message = user2Message,
                        onMessageChanged = {
                            user2Message = it
                        },
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(16.dp),
                        onMessageAdd = {
                            addTextToChat(user2Message, chat)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                ChatArea(
                    chat = chat,
                    onMessageDelete = {
                            message -> chat.remove(message)
                    }
                )
            }
        }
    )


}

private fun addTextToChat(
    user2Message: String,
    chat: SnapshotStateList<Message>
) {
    when{
        user2Message.contains("hello") ->{
            chat.add(Message(user2Message,false))
            chat.add(Message("Morning! how its going",true))
        }
        user2Message.contains("flip")|| user2Message.contains("coin")->{
            val r = (0..1).random()
            val result = if(r==0) "heads" else "tails"
            chat.add(Message(user2Message,false))
            chat.add(Message("I flipped a coin and it landed on $result",true))

        }
        user2Message.contains("time") || user2Message.contains("clock")-> {
            val timeStamp = Timestamp(System.currentTimeMillis())
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val date = sdf.format(Date(timeStamp.time))
            chat.add(Message(user2Message,false))
            chat.add(Message(date.toString(),true))
        }
        else -> {
            chat.add(Message(user2Message,false))
            chat.add(Message("Sorry i did not understand",true))
        }
    }
}
