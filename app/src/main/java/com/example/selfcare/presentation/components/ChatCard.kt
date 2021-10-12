package com.example.selfcare.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.selfcare.R
import com.example.selfcare.data.model.Message

@Composable
fun ChatCard(msg: Message) {
    if (msg.author == "Mascot") {
        Row {
            Image(
                painter = painterResource(R.drawable.mascot_selfie),
                contentDescription = "mascot profile photo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row {
                    Text(msg.author)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(msg.dateTime)
                }
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(msg.body)
                }
                if (msg.options != null) {
                    Spacer(modifier = Modifier.width(80.dp))
                    Row (){
                        msg.options.forEach {
                            Button(onClick = {TODO()}) {
                                Text(it)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        
                    }
                }
            }
            Spacer(modifier = Modifier.width(80.dp))

        }
    } else{
        Row {
            Spacer(modifier = Modifier.width(80.dp))
            Column {
                Row {
                    Text(msg.author)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(msg.dateTime)
                }
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(msg.body)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(R.drawable.mascot_selfie),
                contentDescription = "mascot profile photo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    }
}



